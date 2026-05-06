package com.example.foodsafe.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("{barcode}.json")
    Call<ProductResponse> getProduct(@Path("barcode") String barcode);
}
