package com.example.foodsafe.network;

import com.example.foodsafe.models.Product;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    private int status;
    @SerializedName("status_verbose")
    private String statusVerbose;
    private Product product;
    private String code;

    public int getStatus() { return status; }
    public String getStatusVerbose() { return statusVerbose; }
    public Product getProduct() { return product; }
    public String getCode() { return code; }
}
