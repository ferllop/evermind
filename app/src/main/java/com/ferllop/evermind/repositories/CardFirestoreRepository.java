package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.datastores.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.datastores.DataStore;
import com.ferllop.evermind.repositories.datastores.FirestoreDataStore;
import com.ferllop.evermind.repositories.mappers.CardMapper;
import com.google.firebase.firestore.FirebaseFirestore;

public class CardFirestoreRepository implements CardRepository {

    DataStore<Card> dataStore;

    public CardFirestoreRepository(DatastoreListener listener) {
        this.dataStore = new FirestoreDataStore<>(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.CARD.getValue(),
                new CardMapper(),
                listener
        );
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

    public void getCardsFromUsername(String username) {
        dataStore.getFromUniqueField("authorUsername", username);
    }
}
