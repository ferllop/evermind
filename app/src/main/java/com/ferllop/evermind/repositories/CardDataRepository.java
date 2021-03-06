package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.datastores.CardDataStore;
import com.ferllop.evermind.repositories.datastores.FirestoreCardDataStore;


public class CardDataRepository implements CardRepository {

    CardDataStore dataStore;

    public CardDataRepository(DatastoreListener activity) {
        this.dataStore = new FirestoreCardDataStore(activity);
    }

    @Override
    public void insert(Card card) {
        dataStore.insert(card);
    }

    @Override
    public void load(String id) {
        dataStore.load( id);
    }

    @Override
    public void getAll() {
        dataStore.getAll();
    }

    @Override
    public void getFromSearch(String query) {
        dataStore.getFromSearch(query);
    }

    @Override
    public void update(String id, Card card) {
        dataStore.update(id, card);
    }

    @Override
    public void delete(String id) {
        dataStore.delete(id);
    }


}
