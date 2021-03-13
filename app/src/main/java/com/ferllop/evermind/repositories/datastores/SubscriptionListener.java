package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Subscription;

import java.util.List;

public interface SubscriptionListener {
    void onLoad(List<Subscription> subscriptions);
    void onLoad(Subscription subscription);
    void onNotFound();
    void onError(DataStoreError error);
    void onSave(Subscription subscription);
    void onDelete(String subscriptionID);
}
