package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.datastores.technologies.FirestoreDataStore;
import com.ferllop.evermind.repositories.mappers.CardMapper;

public class FirestoreCardDataStore extends CardDataStore{
    FirestoreDataStore<Card> firestoreDataStore;

    public FirestoreCardDataStore(DatastoreListener<Card> listener) {
        super(listener);
        firestoreDataStore = new FirestoreDataStore<>(FirestoreCollectionsLabels.CARD.getValue(), new CardMapper(), this);
    }

    @Override
    public void insert(Card card) {
        firestoreDataStore.insert(card);
    }

    @Override
    public void load(String id) {
        firestoreDataStore.load(id);
    }

    @Override
    public void getAll() {
        firestoreDataStore.getAll();
    }

    @Override
    public void getFromSearch(String query) {
        firestoreDataStore.getFromSearch(query);
    }

    @Override
    public void update(String id, Card card) {
        firestoreDataStore.update(id, card);
    }

    @Override
    public void delete(String id) {
        firestoreDataStore.delete(id);
    }
}
