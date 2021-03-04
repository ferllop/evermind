package com.ferllop.evermind.models;

public class IdentifiedCard {
    String id;
    Card card;

    public IdentifiedCard(String id, Card card){
        this.id = id;
        this.card = card;
    }

    public Card getCard(){
        return card;
    }

    public String getId(){
        return id;
    }
}
