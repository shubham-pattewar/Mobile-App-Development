package com.example.foodsafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodsafe.R;
import com.example.foodsafe.database.AppDatabase;
import com.example.foodsafe.database.ScanHistory;
import com.example.foodsafe.network.ApiClient;
import com.example.foodsafe.network.ProductResponse;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompareActivity extends AppCompatActivity {

    private View card1, card2;
    private LinearLayout layout1, layout2;
    private TextView placeholder1, placeholder2;
    private ImageView img1, img2;
    private TextView name1, name2, score1, score2;
    
    private ScanHistory product1, product2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        initViews();
    }

    private void initViews() {
        card1 = findViewById(R.id.cardProduct1);
        card2 = findViewById(R.id.cardProduct2);
        layout1 = findViewById(R.id.layoutProduct1);
        layout2 = findViewById(R.id.layoutProduct2);
        placeholder1 = findViewById(R.id.placeholder1);
        placeholder2 = findViewById(R.id.placeholder2);
        img1 = findViewById(R.id.imgProduct1);
        img2 = findViewById(R.id.imgProduct2);
        name1 = findViewById(R.id.nameProduct1);
        name2 = findViewById(R.id.nameProduct2);
        score1 = findViewById(R.id.scoreProduct1);
        score2 = findViewById(R.id.scoreProduct2);

        card1.setOnClickListener(v -> selectProduct(1));
        card2.setOnClickListener(v -> selectProduct(2));
        
        findViewById(R.id.clearButton).setOnClickListener(v -> {
            product1 = null;
            product2 = null;
            updateUI();
        });
    }

    private void selectProduct(int slot) {
        // In a real app, this might open a dialog to pick from history
        // For now, let's just trigger a scan for simplicity or pick the latest
        Toast.makeText(this, "Scan a product to compare", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ScanActivity.class);
        startActivityForResult(intent, slot);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String barcode = data.getStringExtra("barcode");
            if (barcode != null) {
                loadProduct(barcode, requestCode);
            }
        }
    }

    private void loadProduct(String barcode, int slot) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ScanHistory history = AppDatabase.getInstance(this).scanHistoryDao().getByBarcode(barcode);
            runOnUiThread(() -> {
                if (history != null) {
                    if (slot == 1) product1 = history;
                    else product2 = history;
                    updateUI();
                }
            });
        });
    }

    private void updateUI() {
        if (product1 != null) {
            layout1.setVisibility(View.VISIBLE);
            placeholder1.setVisibility(View.GONE);
            name1.setText(product1.getProductName());
            score1.setText(String.valueOf(product1.getHealthScore()));
            Glide.with(this).load(product1.getImageUrl()).into(img1);
        } else {
            layout1.setVisibility(View.GONE);
            placeholder1.setVisibility(View.VISIBLE);
        }

        if (product2 != null) {
            layout2.setVisibility(View.VISIBLE);
            placeholder2.setVisibility(View.GONE);
            name2.setText(product2.getProductName());
            score2.setText(String.valueOf(product2.getHealthScore()));
            Glide.with(this).load(product2.getImageUrl()).into(img2);
        } else {
            layout2.setVisibility(View.GONE);
            placeholder2.setVisibility(View.VISIBLE);
        }
        
        compareScores();
    }

    private void compareScores() {
        if (product1 != null && product2 != null) {
            if (product1.getHealthScore() > product2.getHealthScore()) {
                score1.setTextColor(getResources().getColor(R.color.neon_green));
                score2.setTextColor(getResources().getColor(R.color.danger_red));
            } else if (product2.getHealthScore() > product1.getHealthScore()) {
                score2.setTextColor(getResources().getColor(R.color.neon_green));
                score1.setTextColor(getResources().getColor(R.color.danger_red));
            } else {
                score1.setTextColor(getResources().getColor(R.color.neon_green));
                score2.setTextColor(getResources().getColor(R.color.neon_green));
            }
        }
    }
}
