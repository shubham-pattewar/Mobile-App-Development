package com.example.subtrack.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscriptions")
public class Subscription {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String name;           // e.g. "Netflix"
    public String category;       // e.g. "Entertainment"
    public double cost;           // e.g. 15.99
    public String currency;       // e.g. "USD"
    public String billingCycle;   // "monthly" | "yearly" | "weekly" | "custom"
    public int customCycleDays;   // used when billingCycle = "custom"
    public long startDate;        // Unix timestamp
    public long nextRenewalDate;  // Unix timestamp
    public String colorTag;       // hex color e.g. "#E50914"
    public String notes;
    public boolean isActive;
    public int reminderDaysBefore; // 0 = no reminder, 1/3/7
    public String paymentMethod;   // e.g. "Credit Card", "Paytm"
    public boolean isTrial;
    public long trialEndDate;      // Unix timestamp
    
    // Default constructor for Room
    public Subscription() {}
}
