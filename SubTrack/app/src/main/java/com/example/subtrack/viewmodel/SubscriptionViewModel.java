package com.example.subtrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.subtrack.data.model.Subscription;
import com.example.subtrack.data.repository.SubscriptionRepository;

import java.util.List;

public class SubscriptionViewModel extends AndroidViewModel {

    private final SubscriptionRepository repository;
    private final LiveData<List<Subscription>> allSubscriptions;
    private final LiveData<List<Subscription>> activeSubscriptions;

    public SubscriptionViewModel(@NonNull Application application) {
        super(application);
        repository = new SubscriptionRepository(application);
        allSubscriptions = repository.getAllSubscriptions();
        activeSubscriptions = repository.getActiveSubscriptions();
    }

    public LiveData<List<Subscription>> getAllSubscriptions() {
        return allSubscriptions;
    }

    public LiveData<List<Subscription>> getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public LiveData<Subscription> getSubscriptionById(int id) {
        return repository.getSubscriptionById(id);
    }

    public void insert(Subscription subscription) {
        repository.insert(subscription);
    }

    public void update(Subscription subscription) {
        repository.update(subscription);
    }

    public void delete(Subscription subscription) {
        repository.delete(subscription);
    }
}
