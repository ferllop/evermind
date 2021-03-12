package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Subscription;

import java.util.List;

public interface SubscriptionListener {
    void onCount(int count);
    void onLoad(List<Subscription> subs);
}
