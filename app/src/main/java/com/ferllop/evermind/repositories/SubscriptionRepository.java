package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.datastores.SubscriptionFirestoreDataStore;
import com.ferllop.evermind.repositories.fields.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.fields.SubscriptionField;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;
import com.ferllop.evermind.repositories.mappers.SubscriptionMapper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubscriptionRepository{
    final String TAG = "MYAPP-Subs";

    SubscriptionFirestoreDataStore dataStore;

    public SubscriptionRepository(SubscriptionDataStoreListener listener) {
        this.dataStore = new SubscriptionFirestoreDataStore(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.SUBSCRIPTION.getValue(),
                new SubscriptionMapper(),
                listener
        );
    }

    public void insert(Subscription subscription) {
        dataStore.insert(subscription);
    }

    private void updateLevel(String id, Level newLevel) {
        Timestamp nextReview = new Timestamp(86400 * newLevel.getValue() + Timestamp.now().getSeconds(), 0);
        dataStore.updateLevel(id, newLevel, nextReview);
    }

    public void upgradeLevel(Subscription sub){
        updateLevel(sub.getId(), sub.getLevel().next());
    }

    public void downgradeLevel(Subscription sub){
        updateLevel(sub.getId(), sub.getLevel().previous());
    }

    public void delete(String id) {
        dataStore.delete(id);
    }

    public void getAllFromUser(String userID) {
        dataStore.getFromUniqueField(SubscriptionField.USER_ID.getValue(), userID);
    }

    public void subscribeUserToCard(String userID, String cardID){
        if(!SubscriptionsGlobal.getInstance().isUserSubscribedTo(userID, cardID)) {
            Subscription sub = new Subscription(userID, cardID, Level.LEVEL_0, Timestamp.now(), Timestamp.now());
            dataStore.insert(sub);
        }
    }

    public void deleteSubscriptionsWithCardID(String id) {
        dataStore.deleteAllWithCardID(id);
    }
}
