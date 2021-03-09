package com.ferllop.evermind.repositories.fields;

public enum SubscriptionField {
    USER_ID("userID"),
    CARD_ID("cardID"),
    LEVEL("level"),
    LAST_REVIEW("lastReview"),
    NEXT_REVIEW("nextReview");

    private final String value;

    SubscriptionField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
