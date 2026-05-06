package com.example.subtrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.subtrack.data.model.Subscription;

import java.util.List;

@Dao
public interface SubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Subscription subscription);

    @Update
    void update(Subscription subscription);

    @Delete
    void delete(Subscription subscription);

    @Query("SELECT * FROM subscriptions ORDER BY name ASC")
    LiveData<List<Subscription>> getAllSubscriptions();
    
    @Query("SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY nextRenewalDate ASC")
    LiveData<List<Subscription>> getActiveSubscriptions();

    // Synchronous read for WorkManager background tasks
    @Query("SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY nextRenewalDate ASC")
    List<Subscription> getActiveSubscriptionsSync();

    @Query("SELECT * FROM subscriptions WHERE id = :id LIMIT 1")
    LiveData<Subscription> getSubscriptionById(int id);
}
