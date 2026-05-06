package com.example.foodsafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsafe.R;
import com.example.foodsafe.adapters.HistoryAdapter;
import com.example.foodsafe.database.AppDatabase;
import com.example.foodsafe.database.ScanHistory;

import java.util.List;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private TextView totalScansText, avgScoreText;
    private RecyclerView savedProductsRecyclerView;
    private HistoryAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        totalScansText = findViewById(R.id.totalScansText);
        avgScoreText = findViewById(R.id.avgScoreText);
        savedProductsRecyclerView = findViewById(R.id.savedProductsRecyclerView);
        
        db = AppDatabase.getInstance(this);
        
        savedProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        savedProductsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(history -> {
            Intent intent = new Intent(DashboardActivity.this, ProductDetailActivity.class);
            intent.putExtra("barcode", history.getBarcode());
            startActivity(intent);
        });

        loadDashboardData();
    }

    private void loadDashboardData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ScanHistory> allHistory = db.scanHistoryDao().getAllHistory();
            List<ScanHistory> savedProducts = db.scanHistoryDao().getSavedProducts();

            int totalScans = allHistory.size();
            int totalScore = 0;
            for (ScanHistory h : allHistory) {
                totalScore += h.getHealthScore();
            }
            int avgScore = totalScans > 0 ? totalScore / totalScans : 0;

            runOnUiThread(() -> {
                totalScansText.setText(String.valueOf(totalScans));
                avgScoreText.setText(String.valueOf(avgScore));
                adapter.setHistoryList(savedProducts);
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
    }
}
