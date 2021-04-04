package com.ferllop.evermind.repositories.fields;

public enum UserField {
    USER_ID("userID"),
    AUTH_ID("authID"),
    NAME("name"),
    USERNAME("username"),
    EMAIL("email"),
    STATUS("status"),
    LAST_LOGIN("lastLogin"),
    LAST_CONNECTION("lastConnection"),
    SIGN_IN("signIn"),
    DAY_START_TIME("dayStartTime");

    private final String value;

    UserField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
