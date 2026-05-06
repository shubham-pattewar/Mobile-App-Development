package com.example.subtrack.data.model;

public class SubscriptionTemplate {
    public String name;
    public double defaultCost;
    public String category;
    public String billingCycle;
    public String colorHex;

    public SubscriptionTemplate(String name, double defaultCost, String category, String billingCycle, String colorHex) {
        this.name = name;
        this.defaultCost = defaultCost;
        this.category = category;
        this.billingCycle = billingCycle;
        this.colorHex = colorHex;
    }
}
