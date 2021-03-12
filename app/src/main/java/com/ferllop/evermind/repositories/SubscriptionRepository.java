package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.google.firebase.Timestamp;

public interface SubscriptionRepository {
    void insert(Subscription subscription);
    void loadForToday(String id);
    void getAll();
    void updateLevel(String id, Level newLevel, Timestamp nextReview);
    void delete(String id);
    void upgradeLevel(Subscription sub);
    void downgradeLevel(Subscription sub);
}

