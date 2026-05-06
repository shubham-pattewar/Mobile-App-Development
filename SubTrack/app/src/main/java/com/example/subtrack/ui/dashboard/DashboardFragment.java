package com.example.subtrack.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.databinding.FragmentDashboardBinding;
import com.example.subtrack.viewmodel.SubscriptionViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SubscriptionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);
        
        setupPieChart();

        viewModel.getActiveSubscriptions().observe(getViewLifecycleOwner(), subscriptions -> {
            updateSummaryCards(subscriptions);
            updatePieChart(subscriptions);
        });
    }

    private void updateSummaryCards(List<Subscription> subscriptions) {
        int activeCount = subscriptions.size();
        double totalMonthlyCost = 0.0;

        for (Subscription sub : subscriptions) {
            double cost = sub.cost;
            // Normalize all costs to a monthly equivalent
            if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost / 12.0;
            } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost * 4.33; // Approx weeks in a month
            }
            totalMonthlyCost += cost;
        }

        binding.textActiveCount.setText(String.valueOf(activeCount));
        binding.textMonthlySpend.setText(String.format(Locale.getDefault(), "₹%.2f", totalMonthlyCost));
    }

    private void setupPieChart() {
        PieChart chart = binding.pieChart;
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.TRANSPARENT);
        chart.setTransparentCircleColor(Color.TRANSPARENT);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setCenterTextColor(Color.WHITE);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.getLegend().setEnabled(false); // Hide legend for cleaner look
    }

    private void updatePieChart(List<Subscription> subscriptions) {
        Map<String, Float> categoryCosts = new HashMap<>();

        for (Subscription sub : subscriptions) {
            double cost = sub.cost;
            if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost / 12.0;
            } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost * 4.33;
            }
            
            String cat = sub.category;
            if (cat == null || cat.isEmpty()) {
                cat = "Other";
            }
            
            categoryCosts.put(cat, categoryCosts.getOrDefault(cat, 0f) + (float) cost);
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoryCosts.entrySet()) {
            if (entry.getValue() > 0) {
                entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // Map colors from our latest "Royal Violet & Electric Cyan" theme
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#8B5CF6")); // Primary (Royal Violet)
        colors.add(Color.parseColor("#06B6D4")); // Secondary (Electric Cyan)
        colors.add(Color.parseColor("#D946EF")); // Tertiary (Bright Fuchsia)
        colors.add(Color.parseColor("#22C55E")); // Success (Green)
        colors.add(Color.parseColor("#F43F5E")); // Vibrant Rose (Alternative)
        
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(binding.pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        
        binding.pieChart.setData(data);
        binding.pieChart.highlightValues(null);
        binding.pieChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
