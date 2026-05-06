package com.example.foodsafe.models;

import com.google.gson.annotations.SerializedName;

public class Nutriments {
    @SerializedName("energy-kcal_100g")
    private double energy;

    @SerializedName("fat_100g")
    private double fat;

    @SerializedName("sugars_100g")
    private double sugars;

    @SerializedName("proteins_100g")
    private double proteins;

    @SerializedName("salt_100g")
    private double salt;

    @SerializedName("fiber_100g")
    private double fiber;

    public double getEnergy() { return energy; }
    public double getFat() { return fat; }
    public double getSugars() { return sugars; }
    public double getProteins() { return proteins; }
    public double getSalt() { return salt; }
    public double getFiber() { return fiber; }
}
