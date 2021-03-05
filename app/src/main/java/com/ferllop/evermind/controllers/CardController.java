package com.ferllop.evermind.controllers;

import android.app.Activity;
import android.widget.EditText;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.CardDataRepository;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.DatastoreListener;

public class CardController {
    DatastoreListener listener;

    public CardController(DatastoreListener listener) {
        this.listener = listener;
    }


    public void insert(Card card) {
        getRepositoryService().insert(card);
    }


    public void load(String id) {
        getRepositoryService().load(id);
    }


    public void getAll() {
        getRepositoryService().getAll();
    }


    public void getFromSearch(String query) {
        getRepositoryService().getFromSearch(query);
    }


    public void update(String id, Card card) {
        getRepositoryService().update(id, card);
    }


    public void delete(String id) {
        getRepositoryService().delete(id);
    }


    public CardRepository getRepositoryService(){
        return new CardDataRepository(listener);
    }

}
