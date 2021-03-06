package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.DatastoreListener;

public abstract class CardDataStore implements DatastoreListener<Card>{
    DatastoreListener<Card> listener;

    public CardDataStore(DatastoreListener<Card> listener) {
        this.listener = listener;
    }

    @Override
    public void onLoad(Card item) {
        listener.onLoad(item);
    }

    @Override
    public void onDelete() {
        listener.onDelete();
    }

    @Override
    public void onError(String error) {
        listener.onError(error);
    }

    @Override
    public void onSave() {
        listener.onSave();
    }

    public abstract void insert(Card card);
    public abstract void load(String id);
    public abstract void getAll();
    public abstract void getFromSearch(String query);
    public abstract void update(String id, Card card);
    public abstract void delete(String id);

}
