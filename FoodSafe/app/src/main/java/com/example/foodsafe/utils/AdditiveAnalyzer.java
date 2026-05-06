package com.example.foodsafe.utils;

import java.util.HashMap;
import java.util.Map;

public class AdditiveAnalyzer {
    private static final Map<String, String> additives = new HashMap<>();

    static {
        additives.put("en:e102", "HARMFUL");    // Tartrazine
        additives.put("en:e621", "HARMFUL");    // MSG
        additives.put("en:e300", "SAFE");       // Vitamin C
        additives.put("en:e330", "SAFE");       // Citric Acid
        additives.put("en:e129", "CONTROVERSIAL"); // Allura Red
        additives.put("en:e211", "HARMFUL");    // Sodium Benzoate
        additives.put("en:e951", "CONTROVERSIAL"); // Aspartame
        additives.put("en:e171", "HARMFUL");    // Titanium Dioxide
    }

    public static String getRiskLevel(String tag) {
        String level = additives.get(tag.toLowerCase());
        return level != null ? level : "UNKNOWN";
    }
}
