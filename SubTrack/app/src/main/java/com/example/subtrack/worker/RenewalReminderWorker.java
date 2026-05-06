package com.example.subtrack.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.subtrack.data.database.AppDatabase;
import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.utils.NotificationUtils;

import java.util.List;

public class RenewalReminderWorker extends Worker {

    public RenewalReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        NotificationUtils.createNotificationChannel(context);

        AppDatabase db = AppDatabase.getDatabase(context);
        
        // Fetch data synchronously since we are already on a background thread in WorkManager
        List<Subscription> activeSubscriptions = db.subscriptionDao().getActiveSubscriptionsSync();

        if (activeSubscriptions != null) {
            long currentTime = System.currentTimeMillis();
            long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000;

            for (Subscription sub : activeSubscriptions) {
                long timeUntilRenewal = sub.nextRenewalDate - currentTime;
                
                // If renewal is within the next 7 days and in the future
                if (timeUntilRenewal > 0 && timeUntilRenewal <= sevenDaysInMillis) {
                    int daysLeft = (int) (timeUntilRenewal / (1000 * 60 * 60 * 24));
                    
                    String title = "Upcoming Renewal: " + sub.name;
                    String message = String.format("Your %s subscription for ₹%.2f renews in %d days.",
                            sub.name, sub.cost, daysLeft);
                            
                    NotificationUtils.sendNotification(context, sub.id, title, message);
                }
            }
        }

        return Result.success();
    }
}
