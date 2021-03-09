package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.datastores.DataStore;
import com.ferllop.evermind.repositories.datastores.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.datastores.FirestoreDataStore;
import com.ferllop.evermind.repositories.mappers.SubscriptionMapper;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubscriptionFirestoreRepository implements SubscriptionRepository {

    DataStore<Subscription> dataStore;

    public SubscriptionFirestoreRepository(DatastoreListener<Subscription> listener) {
        this.dataStore = new FirestoreDataStore<>(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.SUBSCRIPTION.getValue(),
                new SubscriptionMapper(),
                listener
        );
    }

    @Override
    public void insert(Subscription subscription) {
        dataStore.insert(subscription);
    }

    @Override
    public void loadForToday(String id) {

    }

    @Override
    public void getAll() {

    }

    @Override
    public void updateLevel(String id, Level newLevel) {

    }

    @Override
    public void delete(String id) {

    }
}
