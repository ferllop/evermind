package com.ferllop.evermind.models;

import com.google.firebase.Timestamp;

public class Subscription extends Model{
    String userID;
    String cardID;
    Level level;
    Timestamp lastReview;
    Timestamp nextReview;

    public Subscription(String id, String userID, String cardID, Level level, Timestamp lastReview, Timestamp nextReview) {
        setId(id);
        this.userID = userID;
        this.cardID = cardID;
        this.level = level;
        this.lastReview = lastReview;
        this.nextReview = nextReview;
    }

    public Subscription(String userID, String cardID, Level level, Timestamp lastReview, Timestamp nextReview) {
        this.userID = userID;
        this.cardID = cardID;
        this.level = level;
        this.lastReview = lastReview;
        this.nextReview = nextReview;
    }

    public String getUserID() {
        return userID;
    }

    public String getCardID() {
        return cardID;
    }

    public Level getLevel() {
        return level;
    }

    public Timestamp getLastReview() {
        return lastReview;
    }

    public Timestamp getNextReview() {
        return nextReview;
    }

    public void setId(String id){
        super.setId(id);
    }

    public boolean isToReviewToday(){
        return this.nextReview.getSeconds() <= Timestamp.now().getSeconds();
    }



}
