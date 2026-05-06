package com.example.foodsafe.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodsafe.R;
import com.example.foodsafe.database.AppDatabase;
import com.example.foodsafe.database.ScanHistory;
import com.example.foodsafe.models.Nutriments;
import com.example.foodsafe.models.Product;
import com.example.foodsafe.network.ApiClient;
import com.example.foodsafe.network.ProductResponse;
import com.example.foodsafe.utils.HealthScoreCalculator;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, brandName, healthScore, nutriGrade, ingredients;
    private com.example.foodsafe.ui.CustomProgressBar healthScoreProgress;
    private HorizontalBarChart nutritionChart;
    private Button saveButton;
    private AppDatabase db;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initViews();
        db = AppDatabase.getInstance(this);

        String barcode = getIntent().getStringExtra("barcode");
        if (barcode != null) {
            fetchProductDetails(barcode);
        }
    }

    private void initViews() {
        productImage = findViewById(R.id.detailProductImage);
        productName = findViewById(R.id.detailProductName);
        brandName = findViewById(R.id.detailBrandName);
        healthScore = findViewById(R.id.detailHealthScore);
        healthScoreProgress = findViewById(R.id.healthScoreProgress);
        nutriGrade = findViewById(R.id.detailNutriGrade);
        ingredients = findViewById(R.id.detailIngredients);
        nutritionChart = findViewById(R.id.nutritionChart);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void fetchProductDetails(String barcode) {
        ApiClient.getApiService().getProduct(barcode).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getProduct() != null) {
                    currentProduct = response.body().getProduct();
                    currentProduct.setCode(barcode);
                    displayProduct(currentProduct);
                    addToHistory(currentProduct);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProduct(Product product) {
        productName.setText(product.getProductName());
        brandName.setText(product.getBrands());
        nutriGrade.setText(product.getNutritionGrades() != null ? product.getNutritionGrades().toUpperCase() : "N/A");
        ingredients.setText(product.getIngredientsText());

        int score = HealthScoreCalculator.calculate(product.getNutriments());
        healthScore.setText(String.valueOf(score));
        healthScoreProgress.setProgress(score);

        Glide.with(this).load(product.getImageUrl()).into(productImage);

        setupChart(product.getNutriments());
    }

    private void setupChart(Nutriments n) {
        if (n == null) return;

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) n.getFat()));
        entries.add(new BarEntry(1, (float) n.getSugars()));
        entries.add(new BarEntry(2, (float) n.getProteins()));
        entries.add(new BarEntry(3, (float) n.getSalt()));

        BarDataSet dataSet = new BarDataSet(entries, "Nutrients");
        dataSet.setColor(getResources().getColor(R.color.neon_green));
        dataSet.setValueTextColor(getResources().getColor(R.color.text_primary));

        BarData barData = new BarData(dataSet);
        nutritionChart.setData(barData);
        nutritionChart.getDescription().setEnabled(false);
        nutritionChart.getXAxis().setTextColor(getResources().getColor(R.color.text_secondary));
        nutritionChart.getAxisLeft().setTextColor(getResources().getColor(R.color.text_secondary));
        nutritionChart.getAxisRight().setEnabled(false);
        nutritionChart.invalidate();
    }

    private void addToHistory(Product product) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ScanHistory history = new ScanHistory();
            history.setBarcode(product.getCode());
            history.setProductName(product.getProductName());
            history.setBrandName(product.getBrands());
            history.setHealthScore(HealthScoreCalculator.calculate(product.getNutriments()));
            history.setNutritionGrade(product.getNutritionGrades());
            history.setImageUrl(product.getImageUrl());
            history.setScanDate(System.currentTimeMillis());
            
            ScanHistory existing = db.scanHistoryDao().getByBarcode(product.getCode());
            if (existing == null) {
                db.scanHistoryDao().insert(history);
            } else {
                history.setId(existing.getId());
                history.setSaved(existing.isSaved());
                db.scanHistoryDao().update(history);
            }
        });
    }

    private void saveProduct() {
        if (currentProduct == null) return;
        Executors.newSingleThreadExecutor().execute(() -> {
            ScanHistory history = db.scanHistoryDao().getByBarcode(currentProduct.getCode());
            if (history != null) {
                history.setSaved(true);
                db.scanHistoryDao().update(history);
                runOnUiThread(() -> Toast.makeText(this, "Product Saved!", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
