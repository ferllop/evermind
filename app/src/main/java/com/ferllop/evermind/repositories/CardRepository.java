package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.datastores.CardFirestoreDataStore;
import com.ferllop.evermind.repositories.fields.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.mappers.CardMapper;
import com.google.firebase.firestore.FirebaseFirestore;

public class CardRepository {
    final String TAG = "MYAPP-CardRepo";

    CardFirestoreDataStore dataStore;

    public CardRepository(CardDataStoreListener listener) {
        this.dataStore = new CardFirestoreDataStore(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.CARD.getValue(),
                new CardMapper(),
                listener
        );
    }

    public void insert(Card card) {
        dataStore.insert(card);
    }

    public void load(String id) {
        dataStore.load( id);
    }

    public void getAll() {
        dataStore.getAll();
    }

    public void getFromSearch(String query) {
        dataStore.getFromSearch(query);
    }

    public void update(String id, Card card) {
        dataStore.update(id, card);
    }

    public void delete(String id) {
        dataStore.delete(id);
    }

}
