package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsGlobal {

    List<Subscription> subsList;
    int pointer;

    private static SubscriptionsGlobal subscriptionsGlobal;

    private SubscriptionsGlobal() {
        setSubsList(new ArrayList<>());
    }

    public static SubscriptionsGlobal getInstance(){
        if (subscriptionsGlobal == null) {
            subscriptionsGlobal = new SubscriptionsGlobal();
        }
        return subscriptionsGlobal;
    }

    public List<Subscription> getSubscriptions(){
        return subsList;
    }

    public void setSubsList(List<Subscription> subsList) {
        this.subsList = subsList;
        pointer = 0;
    }

    public boolean hasNext(){
        return pointer < subsList.size();
    }

    public void next(){
        pointer++;
    }

    public List<Subscription> getTenOrRemaining(){
        List<Subscription> result;
        if(subsList.size() > 10){
            result = subsList.subList(pointer, pointer + 10);
        } else {
            result = subsList.subList(pointer, subsList.size());
        }
        pointer += result.size();
        return result;
    }

    public int getPosition(){
        return pointer;
    }
}
