package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;

public interface SubscriptionRepository {
    void insert(Subscription subscription);
    void loadForToday(String id);
    void getAll();
    void updateLevel(String id, Level newLevel);
    void delete(String id);
}

