package com.ferllop.evermind.repositories;

import android.util.Log;

import com.ferllop.evermind.models.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsGlobal {
    final String TAG = "MYAPP";

    List<Subscription> allSubscriptions;
    int pointer;

    private static SubscriptionsGlobal subscriptionsGlobal;

    private SubscriptionsGlobal() {
        setAllSubscriptions(new ArrayList<>());
    }

    public static SubscriptionsGlobal getInstance(){
        if (subscriptionsGlobal == null) {
            subscriptionsGlobal = new SubscriptionsGlobal();
        }
        return subscriptionsGlobal;
    }

    public List<Subscription> getSubscriptions(){
        return allSubscriptions;
    }

    public void setAllSubscriptions(List<Subscription> allSubscriptions) {
        this.allSubscriptions = allSubscriptions;
        pointer = 0;
    }

    public List<Subscription> getTenOrRemainingForToday(int todayStartTime){
        List<Subscription> subsForToday = getSubscriptionsForToday(todayStartTime);
        List<Subscription> result;
        if(subsForToday.size() > 10){
            result = subsForToday.subList(pointer, pointer + 10);
        } else {
            result = subsForToday.subList(pointer, subsForToday.size());
        }
        pointer += result.size();
        return result;
    }

    private List<Subscription> extractSubscriptionsForToday(int todayStartTime){
        List<Subscription> result = new ArrayList<>();
        for (Subscription sub : allSubscriptions) {
            if (sub.isToReviewToday(todayStartTime)){
                result.add(sub);
            }
        }
        return result;
    }

    public List<Subscription> getSubscriptionsForToday(int todayStartTime){
        return extractSubscriptionsForToday(todayStartTime);
    }

    public String getSubscriptionID(String userID, String cardID){
        for (Subscription sub : allSubscriptions){
            if (sub.getUserID().equals(userID) && sub.getCardID().equals(cardID)){
                return sub.getId();
            }
        }
        return null;
    }

    public void addSubscription(Subscription subscription) {
       List<Subscription> clone = allSubscriptions;
       clone.add(subscription);
       setAllSubscriptions(clone);
    }

    public void deleteSubscription(String subscriptionID) {
        List<Subscription> result = new ArrayList<>();
        for(Subscription sub : allSubscriptions){
            if (!sub.getId().equals(subscriptionID)){
                result.add(sub);
            }
        }
        setAllSubscriptions(result);
    }

    public boolean isUserSubscribedTo(String userID, String cardID) {
        for(Subscription sub : allSubscriptions){
            if (sub.getCardID().equals(cardID) && sub.getUserID().equals(userID)){
                return true;
            }
        }
        return false;
    }

    public String getCardIdFrom(String subscriptionID) {
        for(Subscription sub : allSubscriptions){
            Log.d(TAG, "getCardIdFrom: " + subscriptionID + " -- "+ sub.getId());
            if (sub.getId().equals(subscriptionID)){
                return sub.getCardID();
            }
        }
        return null;
    }

    public double getProgress(int done, int toDo) {
        return ((double) toDo - done - 1) / toDo * 100;
    }
}
