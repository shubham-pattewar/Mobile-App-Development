package com.example.subtrack.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subtrack.R;
import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.databinding.FragmentSubscriptionListBinding;
import com.example.subtrack.viewmodel.SubscriptionViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SubscriptionListFragment extends Fragment {

    private SubscriptionViewModel subscriptionViewModel;
    private FragmentSubscriptionListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSubscriptionListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SubscriptionAdapter adapter = new SubscriptionAdapter();
        binding.recyclerView.setAdapter(adapter);

        subscriptionViewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);
        
        subscriptionViewModel.getAllSubscriptions().observe(getViewLifecycleOwner(), subscriptions -> {
            adapter.submitList(subscriptions);
            if (subscriptions.isEmpty()) {
                binding.emptyStateLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.emptyStateLayout.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.fabAddSubscription.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(requireContext(), com.example.subtrack.ui.addedit.AddEditSubscriptionActivity.class);
            startActivity(intent);
        });

        // Swipe to delete with undo
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Subscription deletedSub = adapter.getSubscriptionAt(position);
                subscriptionViewModel.delete(deletedSub);

                Snackbar.make(binding.coordinatorLayout, "Subscription deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> subscriptionViewModel.insert(deletedSub))
                        .show();
            }
        }).attachToRecyclerView(binding.recyclerView);

        adapter.setOnItemClickListener(subscription -> {
            android.content.Intent intent = new android.content.Intent(requireContext(), com.example.subtrack.ui.addedit.AddEditSubscriptionActivity.class);
            intent.putExtra(com.example.subtrack.ui.addedit.AddEditSubscriptionActivity.EXTRA_SUB_ID, subscription.id);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
