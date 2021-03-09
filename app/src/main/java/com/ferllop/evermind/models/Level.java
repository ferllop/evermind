package com.ferllop.evermind.models;

public enum UserCardLevel {
    LEVEL1(1),
    LEVEL2(3),
    LEVEL3(7),
    LEVEL4(15),
    LEVEL5(30),
    LEVEL6(60),
    LEVEL7(120);

    private final int value;

    UserCardLevel(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
