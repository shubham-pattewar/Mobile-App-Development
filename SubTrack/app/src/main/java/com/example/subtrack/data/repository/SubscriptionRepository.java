package com.example.subtrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.subtrack.data.dao.SubscriptionDao;
import com.example.subtrack.data.database.AppDatabase;
import com.example.subtrack.data.model.Subscription;

import java.util.List;

public class SubscriptionRepository {
    private SubscriptionDao subscriptionDao;
    private LiveData<List<Subscription>> allSubscriptions;
    private LiveData<List<Subscription>> activeSubscriptions;

    public SubscriptionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        subscriptionDao = db.subscriptionDao();
        allSubscriptions = subscriptionDao.getAllSubscriptions();
        activeSubscriptions = subscriptionDao.getActiveSubscriptions();
    }

    public LiveData<List<Subscription>> getAllSubscriptions() {
        return allSubscriptions;
    }

    public LiveData<List<Subscription>> getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public LiveData<Subscription> getSubscriptionById(int id) {
        return subscriptionDao.getSubscriptionById(id);
    }

    public void insert(Subscription subscription) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            subscriptionDao.insert(subscription);
        });
    }

    public void update(Subscription subscription) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            subscriptionDao.update(subscription);
        });
    }

    public void delete(Subscription subscription) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            subscriptionDao.delete(subscription);
        });
    }
}
