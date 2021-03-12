package com.ferllop.evermind.repositories;

import android.util.Log;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.datastores.SubscriptionListener;
import com.ferllop.evermind.repositories.datastores.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.datastores.FirestoreSubscriptionDataStore;
import com.ferllop.evermind.repositories.mappers.SubscriptionMapper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;

public class SubscriptionFirestoreRepository implements SubscriptionRepository, DatastoreListener<Subscription> {
    final String TAG = "MYAPP-Subs";
    FirestoreSubscriptionDataStore dataStore;


    public SubscriptionFirestoreRepository(DatastoreListener<Subscription> subListener, SubscriptionListener listener) {
        this.dataStore = new FirestoreSubscriptionDataStore(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.SUBSCRIPTION.getValue(),
                new SubscriptionMapper(),
                subListener,
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
    public void updateLevel(String id, Level newLevel, Timestamp nextReview) {
        dataStore.updateLevel(id, newLevel, nextReview);
    }

    @Override
    public void upgradeLevel(Subscription sub){
        Level nextLevel = sub.getLevel().next();
        Timestamp nextReview = new Timestamp(86400 * nextLevel.getValue() + Timestamp.now().getSeconds(), 0);
        updateLevel(sub.getId(), nextLevel, nextReview);
    }

    @Override
    public void downgradeLevel(Subscription sub){
        Level previousLevel = sub.getLevel().previous();
        Timestamp nextReview = new Timestamp(86400 * previousLevel.getValue() + Timestamp.now().getSeconds(), 0);
        updateLevel(sub.getId(), previousLevel, nextReview);
    }

    @Override
    public void delete(String id) {

    }

    public void getCountForToday(String userID) {
        dataStore.getCardFromUserToReviewFromDate(userID, Timestamp.now());
        Log.d(TAG, userID);
    }

    @Override
    public void onLoad(Subscription item) {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave() {

    }
}
