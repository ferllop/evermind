package com.ferllop.evermind.models;

public enum Level {
    LEVEL_0(0),
    LEVEL_1(1),
    LEVEL_2(3),
    LEVEL_3(7),
    LEVEL_4(15),
    LEVEL_5(30),
    LEVEL_6(60),
    LEVEL_7(120);

    private final int value;

    Level(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public Level nextLevel(){
        if (this.ordinal() == Level.values().length - 1){
            return this;
        }
        return Level.values()[this.ordinal()];
    }
}
