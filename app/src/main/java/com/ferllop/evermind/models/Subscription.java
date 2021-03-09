package com.ferllop.evermind.models;

public class Subscription extends Model{
    String userID;
    String cardID;
    Level level;
    int lastReview;
    int nextReview;

    public Subscription(String id, String userID, String cardID, Level level, int lastReview, int nextReview) {
        setId(id);
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

    public int getLastReview() {
        return lastReview;
    }

    public int getNextReview() {
        return nextReview;
    }

}
