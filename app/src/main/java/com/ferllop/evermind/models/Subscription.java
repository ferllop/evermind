package com.ferllop.evermind.models;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public boolean isToReviewToday(int dayStartTime){
        return reclockDate(this.nextReview.toDate(), dayStartTime) <= new Date().getTime();
    }

    private long reclockDate(Date date, int dayStartTime){
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        int dateHour = Integer.parseInt(formatter.format(date));
        int diffMilli = (dateHour - dayStartTime) * 60 * 60 * 1000;
        return new Date(date.getTime() - diffMilli).getTime();
    }
}
