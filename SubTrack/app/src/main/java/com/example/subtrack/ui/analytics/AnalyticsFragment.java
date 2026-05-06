package com.example.subtrack.ui.analytics;

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
import com.example.subtrack.databinding.FragmentAnalyticsBinding;
import com.example.subtrack.viewmodel.SubscriptionViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private SubscriptionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);

        setupBarChart();
        setupPaymentMethodChart();

        viewModel.getActiveSubscriptions().observe(getViewLifecycleOwner(), subscriptions -> {
            if (subscriptions != null && !subscriptions.isEmpty()) {
                updateInsights(subscriptions);
                updateBarChart(subscriptions);
                updatePaymentMethodChart(subscriptions);
            } else {
                binding.textMostExpensive.setText("No active subscriptions");
                binding.textSavingsSuggestion.setText("Add subscriptions to see suggestions");
            }
        });
    }

    private void setupBarChart() {
        BarChart chart = binding.barChart;
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
    }

    private void updateBarChart(List<Subscription> subscriptions) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.getDefault());

        double totalMonthlyCost = calculateMonthlyTotal(subscriptions);

        for (int i = 0; i < 6; i++) {
            labels.add(sdf.format(cal.getTime()));
            // Simple projection: assumes constant monthly spend based on current active. 
            // A more complex app would factor in exact renewal dates for annual plans dynamically.
            entries.add(new BarEntry(i, (float) totalMonthlyCost));
            cal.add(Calendar.MONTH, 1);
        }

        binding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Spend");
        dataSet.setColor(Color.parseColor("#06B6D4")); // Secondary color (Electric Cyan) from new theme
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);

        binding.barChart.setData(data);
        binding.barChart.animateY(1000);
        binding.barChart.invalidate();
    }
    
    private double calculateMonthlyTotal(List<Subscription> subscriptions) {
        double total = 0.0;
        for (Subscription sub : subscriptions) {
            double cost = sub.cost;
            if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost / 12.0;
            } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost * 4.33;
            }
            total += cost;
        }
        return total;
    }

    private void updateInsights(List<Subscription> subscriptions) {
        Subscription mostExpensive = null;
        double maxMonthlyCost = -1.0;

        for (Subscription sub : subscriptions) {
            double monthlyCost = sub.cost;
            if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
                monthlyCost = sub.cost / 12.0;
            } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
                monthlyCost = sub.cost * 4.33;
            }

            if (monthlyCost > maxMonthlyCost) {
                maxMonthlyCost = monthlyCost;
                mostExpensive = sub;
            }
        }

        if (mostExpensive != null) {
            String currency = mostExpensive.currency != null ? mostExpensive.currency : "₹";
            binding.textMostExpensive.setText(String.format(Locale.getDefault(), "%s - %s%.2f/%s", 
                    mostExpensive.name, currency, mostExpensive.cost, 
                    mostExpensive.billingCycle != null ? mostExpensive.billingCycle.toLowerCase() : "monthly"));
                    
            // Very simple suggestion logic for the demo
            if (!"Yearly".equalsIgnoreCase(mostExpensive.billingCycle)) {
                double yearlyEstimate = maxMonthlyCost * 12 * 0.8; // Assume 20% discount for annual
                binding.textSavingsSuggestion.setText(String.format(Locale.getDefault(), 
                        "Switch %s to an annual plan to save ~%s%.0f per year.", 
                        mostExpensive.name, currency, (maxMonthlyCost * 12) - yearlyEstimate));
            } else {
                binding.textSavingsSuggestion.setText("You are maximizing savings on your most expensive subscription by paying annually!");
            }
        }
    }

    private void setupPaymentMethodChart() {
        PieChart chart = binding.paymentMethodChart;
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.TRANSPARENT);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.setCenterText("Payment Methods");
        chart.setCenterTextColor(Color.WHITE);
        chart.setCenterTextSize(14f);
    }

    private void updatePaymentMethodChart(List<Subscription> subscriptions) {
        Map<String, Float> methodTotals = new HashMap<>();

        for (Subscription sub : subscriptions) {
            float cost = (float) sub.cost;
            if ("Yearly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost / 12.0f;
            } else if ("Weekly".equalsIgnoreCase(sub.billingCycle)) {
                cost = cost * 4.33f;
            }
            
            String method = sub.paymentMethod != null && !sub.paymentMethod.isEmpty() ? sub.paymentMethod : "Unknown";
            methodTotals.put(method, methodTotals.getOrDefault(method, 0f) + cost);
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : methodTotals.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#8B5CF6")); // Royal Violet
        colors.add(Color.parseColor("#06B6D4")); // Electric Cyan
        colors.add(Color.parseColor("#D946EF")); // Bright Fuchsia
        colors.add(Color.parseColor("#22C55E")); // Success Green
        colors.add(Color.parseColor("#F43F5E")); // Vibrant Rose
        colors.add(Color.parseColor("#FACC15")); // Gold
        
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        binding.paymentMethodChart.setData(data);
        binding.paymentMethodChart.animateY(1000);
        binding.paymentMethodChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
