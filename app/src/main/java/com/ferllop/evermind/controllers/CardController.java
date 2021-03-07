package com.ferllop.evermind.controllers;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.CardFirestoreRepository;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.DatastoreListener;

public class CardController {
    CardRepository cardRepository;

    public CardController(DatastoreListener listener) {
        this.cardRepository = getRepositoryService(listener);
    }

    private CardRepository getRepositoryService(DatastoreListener listener){
        return new CardFirestoreRepository(listener);
    }

    public void insert(Card card) {
        cardRepository.insert(card);
    }

    public void insert(String question, String answer, String labels) {
        insert(new Card("anonymous", question, answer, labels));
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

    public void update(String id, String author, String question, String answer, String labels) {
        update(id, new Card(author, question, answer, labels));
    }

    public void delete(String id) {
        cardRepository.delete(id);
    }
}
