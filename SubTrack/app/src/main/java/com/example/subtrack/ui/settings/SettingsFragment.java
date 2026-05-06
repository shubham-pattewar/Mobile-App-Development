package com.example.subtrack.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.subtrack.R;
import com.example.subtrack.data.database.AppDatabase;
import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.utils.NotificationUtils;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ActivityResultLauncher<Intent> createDocumentLauncher;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference darkModePref = findPreference("dark_mode");
        if (darkModePref != null) {
            darkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isDarkMode = (Boolean) newValue;
                if (isDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            });
        }

        Preference exportPref = findPreference("export_csv");
        if (exportPref != null) {
            exportPref.setOnPreferenceClickListener(preference -> {
                exportDataToCsv();
                return true;
            });
        }

        Preference testNotifPref = findPreference("test_notification");
        if (testNotifPref != null) {
            testNotifPref.setOnPreferenceClickListener(preference -> {
                NotificationUtils.createNotificationChannel(requireContext());
                NotificationUtils.sendNotification(requireContext(), 999, 
                        "Test Renewal: Netflix", 
                        "Your Netflix subscription for ₹199 renews in 3 days.");
                Toast.makeText(requireContext(), "Test notification sent!", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
        
        // Register the launcher for the SAF (Storage Access Framework) to save the CSV file
        createDocumentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            writeCsvToUri(data.getData());
                        }
                    }
                }
        );
    }

    private void exportDataToCsv() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "subscriptions_export.csv");
        createDocumentLauncher.launch(intent);
    }

    private void writeCsvToUri(Uri uri) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Fetch data synchronously off the main thread
                AppDatabase db = AppDatabase.getDatabase(requireContext());
                List<Subscription> subscriptions = db.subscriptionDao().getActiveSubscriptionsSync();

                if (subscriptions == null || subscriptions.isEmpty()) {
                    requireActivity().runOnUiThread(() -> 
                        Toast.makeText(requireContext(), "No data to export", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                OutputStream outputStream = requireContext().getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    
                    // Write CSV Header
                    writer.write("Name,Category,Cost,Currency,BillingCycle,NextRenewalTimestamp\n");
                    
                    for (Subscription sub : subscriptions) {
                        String line = String.format("%s,%s,%.2f,%s,%s,%d\n",
                                sub.name.replace(",", " "), // Sanitize commas to avoid breaking CSV format
                                sub.category != null ? sub.category.replace(",", " ") : "None",
                                sub.cost,
                                sub.currency != null ? sub.currency : "₹",
                                sub.billingCycle,
                                sub.nextRenewalDate);
                        writer.write(line);
                    }
                    
                    writer.close();
                    
                    requireActivity().runOnUiThread(() -> 
                        Toast.makeText(requireContext(), "Export Successful!", Toast.LENGTH_LONG).show()
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> 
                    Toast.makeText(requireContext(), "Export Failed", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
