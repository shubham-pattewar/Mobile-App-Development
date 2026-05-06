package com.example.foodsafe.utils;

import com.example.foodsafe.models.Nutriments;

public class HealthScoreCalculator {
    public static int calculate(Nutriments n) {
        if (n == null) return 50;
        
        int score = 100;

        // Deductions
        if (n.getSugars() > 20) score -= 25;
        else if (n.getSugars() > 10) score -= 10;

        if (n.getFat() > 20) score -= 20;
        else if (n.getFat() > 10) score -= 10;

        if (n.getSalt() > 1.5) score -= 15;

        if (n.getEnergy() > 500) score -= 10;

        // Bonuses
        if (n.getProteins() > 10) score += 10;
        if (n.getFiber() > 5) score += 10;

        return Math.max(0, Math.min(score, 100));
    }
    
    public static String getStatus(int score) {
        if (score >= 80) return "Safe";
        if (score >= 50) return "Moderate";
        return "Unsafe";
    }
}
