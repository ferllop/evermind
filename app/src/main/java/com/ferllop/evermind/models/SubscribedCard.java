package com.ferllop.evermind.models;

public class SubscribedCard {
    String cardID;
    Card card;
    Subscription subscription;

    public SubscribedCard(String cardID, Subscription subscription) {
        this.cardID = cardID;
        this.subscription = subscription;
    }

    public String getCardID() {
        return cardID;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Subscription getSubscription() {
        return subscription;
    }

}
