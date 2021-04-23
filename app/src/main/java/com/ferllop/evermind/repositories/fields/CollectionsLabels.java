package com.ferllop.evermind.repositories.fields;

public enum CollectionsLabels {
    CARD("cards"),
    USER("users"),
    SUBSCRIPTION("subscriptions");

    private final String value;

    CollectionsLabels(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
