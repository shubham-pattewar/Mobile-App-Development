package com.example.foodsafe.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Product {
    @SerializedName("product_name")
    private String productName;

    private String brands;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("nutrition_grades")
    private String nutritionGrades;

    private Nutriments nutriments;

    @SerializedName("additives_tags")
    private List<String> additivesTags;

    @SerializedName("ingredients_text")
    private String ingredientsText;

    private String code; // Barcode

    public String getProductName() { return productName; }
    public String getBrands() { return brands; }
    public String getImageUrl() { return imageUrl; }
    public String getNutritionGrades() { return nutritionGrades; }
    public Nutriments getNutriments() { return nutriments; }
    public List<String> getAdditivesTags() { return additivesTags; }
    public String getIngredientsText() { return ingredientsText; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
