package com.example.foodsafe.utils;

import android.graphics.Color;
import com.example.foodsafe.R;

public class NutritionGradeHelper {
    public static int getGradeColor(String grade) {
        if (grade == null) return Color.GRAY;
        switch (grade.toLowerCase()) {
            case "a": return Color.parseColor("#038141");
            case "b": return Color.parseColor("#85BB2F");
            case "c": return Color.parseColor("#FECB02");
            case "d": return Color.parseColor("#EE8100");
            case "e": return Color.parseColor("#E63E11");
            default: return Color.GRAY;
        }
    }

    public static String getGradeImage(String grade) {
        // In a real app, you'd return a drawable resource ID for the Nutri-Score badge
        return grade != null ? grade.toUpperCase() : "?";
    }
}
