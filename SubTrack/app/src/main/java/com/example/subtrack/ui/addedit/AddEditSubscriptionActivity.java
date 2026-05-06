package com.example.subtrack.ui.addedit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subtrack.R;
import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.data.model.SubscriptionTemplate;
import com.example.subtrack.databinding.ActivityAddEditSubscriptionBinding;
import com.example.subtrack.utils.TemplateProvider;
import com.example.subtrack.viewmodel.SubscriptionViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditSubscriptionActivity extends AppCompatActivity {

    public static final String EXTRA_SUB_ID = "com.example.subtrack.EXTRA_SUB_ID";
    
    private ActivityAddEditSubscriptionBinding binding;
    private SubscriptionViewModel viewModel;
    private Subscription currentSubscription;
    private long selectedDate = System.currentTimeMillis();
    private long selectedTrialDate = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000); // Default +7 days

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditSubscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);

        setupToolbar();
        setupTemplates();
        setupCategoryDropdown();
        setupPaymentMethodDropdown();
        setupDatePicker();
        setupTrialSection();
        setupSaveButton();
        setupDeleteButton();

        if (getIntent().hasExtra(EXTRA_SUB_ID)) {
            int subId = getIntent().getIntExtra(EXTRA_SUB_ID, -1);
            if (subId != -1) {
                binding.toolbar.setTitle("Edit Subscription");
                viewModel.getSubscriptionById(subId).observe(this, sub -> {
                    if (sub != null) {
                        currentSubscription = sub;
                        populateFields(sub);
                        binding.btnDelete.setVisibility(android.view.View.VISIBLE);
                        binding.recyclerTemplates.setVisibility(android.view.View.GONE);
                        binding.textTemplatesTitle.setVisibility(android.view.View.GONE);
                    }
                });
            }
        }
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupTemplates() {
        binding.recyclerTemplates.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TemplateAdapter adapter = new TemplateAdapter(TemplateProvider.getPopularTemplates(), template -> {
            binding.editName.setText(template.name);
            binding.editCost.setText(String.valueOf(template.defaultCost));
            binding.editCategory.setText(template.category, false);
            
            if ("Yearly".equalsIgnoreCase(template.billingCycle)) {
                binding.chipGroupCycle.check(R.id.chipYearly);
            } else if ("Weekly".equalsIgnoreCase(template.billingCycle)) {
                binding.chipGroupCycle.check(R.id.chipWeekly);
            } else {
                binding.chipGroupCycle.check(R.id.chipMonthly);
            }
            
            // Optional: Show a small toast to confirm template loaded
            Toast.makeText(this, template.name + " template loaded", Toast.LENGTH_SHORT).show();
        });
        binding.recyclerTemplates.setAdapter(adapter);
    }

    private void setupCategoryDropdown() {
        String[] categories = new String[]{"Entertainment", "Music", "Productivity", "Utilities", "Health", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_dropdown_item_1line, categories);
        binding.editCategory.setAdapter(adapter);
    }

    private void setupPaymentMethodDropdown() {
        String[] methods = new String[]{"Credit Card", "Debit Card", "UPI", "Paytm", "Amazon Pay", "Cash", "Net Banking"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_dropdown_item_1line, methods);
        binding.editPaymentMethod.setAdapter(adapter);
    }

    private void setupDatePicker() {
        updateDateText();
        binding.layoutDate.setEndIconOnClickListener(v -> showDatePicker());
        binding.editDate.setOnClickListener(v -> showDatePicker());
    }
    
    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select start date")
                .setSelection(selectedDate)
                .build();
                
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate = selection;
            updateDateText();
        });
        
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        binding.editDate.setText(sdf.format(new Date(selectedDate)));
    }

    private void setupTrialSection() {
        binding.switchTrial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutTrialDate.setVisibility(isChecked ? android.view.View.VISIBLE : android.view.View.GONE);
        });

        updateTrialDateText();
        binding.layoutTrialDate.setEndIconOnClickListener(v -> showTrialDatePicker());
        binding.editTrialDate.setOnClickListener(v -> showTrialDatePicker());
    }

    private void showTrialDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select trial end date")
                .setSelection(selectedTrialDate)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedTrialDate = selection;
            updateTrialDateText();
        });

        datePicker.show(getSupportFragmentManager(), "TRIAL_DATE_PICKER");
    }

    private void updateTrialDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        binding.editTrialDate.setText(sdf.format(new Date(selectedTrialDate)));
    }

    private void setupSaveButton() {
        binding.fabSave.setOnClickListener(v -> saveSubscription());
    }

    private void setupDeleteButton() {
        binding.btnDelete.setOnClickListener(v -> {
            if (currentSubscription != null) {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Delete Subscription")
                        .setMessage("Are you sure you want to delete this subscription?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            viewModel.delete(currentSubscription);
                            Toast.makeText(this, "Subscription deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    private void populateFields(Subscription sub) {
        binding.editName.setText(sub.name);
        binding.editCost.setText(String.valueOf(sub.cost));
        binding.editCategory.setText(sub.category, false);
        binding.editPaymentMethod.setText(sub.paymentMethod, false);
        binding.editNotes.setText(sub.notes);
        
        binding.switchTrial.setChecked(sub.isTrial);
        if (sub.isTrial) {
            selectedTrialDate = sub.trialEndDate;
            updateTrialDateText();
            binding.layoutTrialDate.setVisibility(android.view.View.VISIBLE);
        }

        selectedDate = sub.startDate;
        updateDateText();

        if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
            binding.chipGroupCycle.check(R.id.chipYearly);
        } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
            binding.chipGroupCycle.check(R.id.chipWeekly);
        } else {
            binding.chipGroupCycle.check(R.id.chipMonthly);
        }
    }

    private void saveSubscription() {
        String name = binding.editName.getText() != null ? binding.editName.getText().toString().trim() : "";
        String costStr = binding.editCost.getText() != null ? binding.editCost.getText().toString().trim() : "";
        String category = binding.editCategory.getText() != null ? binding.editCategory.getText().toString().trim() : "";
        String paymentMethod = binding.editPaymentMethod.getText() != null ? binding.editPaymentMethod.getText().toString().trim() : "";
        String notes = binding.editNotes.getText() != null ? binding.editNotes.getText().toString().trim() : "";

        if (name.isEmpty()) {
            binding.layoutName.setError("Name is required");
            return;
        } else {
            binding.layoutName.setError(null);
        }

        if (costStr.isEmpty()) {
            binding.layoutCost.setError("Cost is required");
            return;
        } else {
            binding.layoutCost.setError(null);
        }

        double cost = Double.parseDouble(costStr);

        String billingCycle = "Monthly";
        int checkedChipId = binding.chipGroupCycle.getCheckedChipId();
        if (checkedChipId == R.id.chipYearly) {
            billingCycle = "Yearly";
        } else if (checkedChipId == R.id.chipWeekly) {
            billingCycle = "Weekly";
        }

        Subscription sub = currentSubscription != null ? currentSubscription : new Subscription();
        sub.name = name;
        sub.cost = cost;
        sub.category = category;
        sub.paymentMethod = paymentMethod;
        sub.billingCycle = billingCycle;
        sub.notes = notes;
        sub.startDate = selectedDate;
        sub.isTrial = binding.switchTrial.isChecked();
        sub.trialEndDate = sub.isTrial ? selectedTrialDate : 0;
        sub.isActive = true;
        sub.currency = "₹";
        
        sub.nextRenewalDate = selectedDate; 
        sub.colorTag = "#006B5F";

        if (currentSubscription == null) {
            viewModel.insert(sub);
            Toast.makeText(this, "Subscription saved", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.update(sub);
            Toast.makeText(this, "Subscription updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
