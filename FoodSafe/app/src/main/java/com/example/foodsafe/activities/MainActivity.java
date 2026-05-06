package com.example.foodsafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsafe.R;
import com.example.foodsafe.adapters.HistoryAdapter;
import com.example.foodsafe.database.AppDatabase;
import com.example.foodsafe.database.ScanHistory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recentScansRecyclerView;
    private HistoryAdapter historyAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        recentScansRecyclerView = findViewById(R.id.recentScansRecyclerView);
        recentScansRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        
        historyAdapter = new HistoryAdapter();
        recentScansRecyclerView.setAdapter(historyAdapter);

        historyAdapter.setOnItemClickListener(history -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("barcode", history.getBarcode());
            startActivity(intent);
        });

        FloatingActionButton scanFab = findViewById(R.id.scanFab);
        scanFab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ScanActivity.class));
        });

        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_scan) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                return true;
            } else if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_compare) {
                startActivity(new Intent(MainActivity.this, CompareActivity.class));
                return true;
            }
            return false;
        });

        com.google.android.material.textfield.TextInputEditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            String query = v.getText().toString();
            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("barcode", query);
                startActivity(intent);
                return true;
            }
            return false;
        });

        loadHistory();
    }

    private void loadHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ScanHistory> history = db.scanHistoryDao().getAllHistory();
            runOnUiThread(() -> {
                historyAdapter.setHistoryList(history);
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
    }
}
