package com.example.foodsafe.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scan_history")
public class ScanHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String barcode;
    private String productName;
    private String brandName;
    private int healthScore;
    private String nutritionGrade;
    private String imageUrl;
    private long scanDate;
    private boolean isSaved;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public int getHealthScore() { return healthScore; }
    public void setHealthScore(int healthScore) { this.healthScore = healthScore; }
    public String getNutritionGrade() { return nutritionGrade; }
    public void setNutritionGrade(String nutritionGrade) { this.nutritionGrade = nutritionGrade; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public long getScanDate() { return scanDate; }
    public void setScanDate(long scanDate) { this.scanDate = scanDate; }
    public boolean isSaved() { return isSaved; }
    public void setSaved(boolean saved) { isSaved = saved; }
}
