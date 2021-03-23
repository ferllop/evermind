package com.ferllop.evermind.repositories.listeners;

import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.Subscription;

import java.util.List;

public interface SubscriptionDataStoreListener{
    void onSave(Subscription subscription);
    void onLoadAll(List<Subscription> subscriptions);
    void onLoad(Subscription item);
    void onDelete(String id);
    void onError(DataStoreError error);
}
