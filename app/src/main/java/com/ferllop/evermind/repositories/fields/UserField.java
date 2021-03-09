package com.ferllop.evermind.repositories.fields;

public enum UserField {
    NAME("name"),
    USERNAME("username"),
    EMAIL("email"),
    PASSWORD("password"),
    STATUS("status"),
    LAST_CONNECTION("lastConnection"),
    SIGN_IN("signIn"),
    SUBSCRIPTIONS("subscriptions");

    private final String value;

    UserField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
