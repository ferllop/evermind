package com.ferllop.evermind.repositories.fields;

public enum UserField {
    EMAIL("email"),
    USERNAME("username"),
    NAME("name"),
    VERIFIED("verified"),
    USER_PREFIX("@");

    private final String value;

    UserField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
