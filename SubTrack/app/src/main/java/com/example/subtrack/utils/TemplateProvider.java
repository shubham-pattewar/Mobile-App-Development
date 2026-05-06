package com.example.subtrack.utils;

import com.example.subtrack.data.model.SubscriptionTemplate;
import java.util.ArrayList;
import java.util.List;

public class TemplateProvider {

    public static List<SubscriptionTemplate> getPopularTemplates() {
        List<SubscriptionTemplate> list = new ArrayList<>();
        list.add(new SubscriptionTemplate("Netflix", 199.0, "Entertainment", "Monthly", "#E50914"));
        list.add(new SubscriptionTemplate("Spotify", 119.0, "Music", "Monthly", "#1DB954"));
        list.add(new SubscriptionTemplate("Amazon Prime", 1499.0, "Entertainment", "Yearly", "#00A8E1"));
        list.add(new SubscriptionTemplate("YouTube Premium", 129.0, "Entertainment", "Monthly", "#FF0000"));
        list.add(new SubscriptionTemplate("ChatGPT Plus", 1950.0, "Productivity", "Monthly", "#10A37F"));
        list.add(new SubscriptionTemplate("Gym", 1500.0, "Health", "Monthly", "#F57C00"));
        list.add(new SubscriptionTemplate("Google One", 130.0, "Utilities", "Monthly", "#4285F4"));
        list.add(new SubscriptionTemplate("Disney+ Hotstar", 899.0, "Entertainment", "Yearly", "#101835"));
        list.add(new SubscriptionTemplate("Apple Music", 99.0, "Music", "Monthly", "#FA243C"));
        return list;
    }
}
