package com.ferllop.evermind.models;

public abstract class Model {
    private String id;

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }
}
