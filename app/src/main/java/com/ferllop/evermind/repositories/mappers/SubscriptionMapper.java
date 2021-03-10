package com.ferllop.evermind.repositories.mappers;

import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.fields.SubscriptionField;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionMapper implements ModelMapper<Subscription> {
    @Override
    public Subscription execute(String id, Map<String, Object> map) {
        String authorID = (String) map.get(SubscriptionField.USER_ID.getValue());
        String cardID = (String) map.get(SubscriptionField.CARD_ID.getValue());
        String level = (String) map.get(SubscriptionField.LEVEL.getValue());
        Timestamp lastReview = (Timestamp) map.get(SubscriptionField.LAST_REVIEW.getValue());
        Timestamp nextReview = (Timestamp) map.get(SubscriptionField.NEXT_REVIEW.getValue());
        return new Subscription(id, authorID, cardID, Level.valueOf(level), lastReview, nextReview);
    }

    @Override
    public Map<String, Object> execute(Subscription subscription) {
        Map<String, Object> result = new HashMap<>();
        result.put(SubscriptionField.USER_ID.getValue(), subscription.getUserID());
        result.put(SubscriptionField.CARD_ID.getValue(), subscription.getCardID());
        result.put(SubscriptionField.LEVEL.getValue(), subscription.getLevel().getValue());
        result.put(SubscriptionField.LAST_REVIEW.getValue(), subscription.getLastReview());
        result.put(SubscriptionField.NEXT_REVIEW.getValue(), subscription.getNextReview());
        return result;
    }
}
