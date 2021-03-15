package com.ferllop.evermind.controllers;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;

public class CardController {
    CardRepository cardRepository;

    public CardController(CardDataStoreListener listener) {
        this.cardRepository = getRepositoryService(listener);
    }

    private CardRepository getRepositoryService(CardDataStoreListener listener){
        return new CardRepository(listener);
    }

    public void insert(Card card) {
        cardRepository.insert(card);
    }

    public void insert(String userID, String username, String question, String answer, String labels) {
        insert(new Card(userID, username, question, answer, labels));
    }

    public void load(String id) {
        cardRepository.load(id);
    }

    public void getAll() {
        cardRepository.getAll();
    }

    public void getFromSearch(String query) {
        cardRepository.getFromSearch(query);
    }

    public void update(String id, Card card) {
        cardRepository.update(id, card);
    }

    public void update(String id, String authorID, String authorUsername, String question, String answer, String labels) {
        update(id, new Card(authorID, authorUsername, question, answer, labels));
    }

    public void delete(String id) {
        cardRepository.delete(id);
    }

}
