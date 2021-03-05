package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.datasource.DataSource;
import com.ferllop.evermind.repositories.datasource.FirestoreCardDataSource;


public class CardDataRepository implements CardRepository {

    DataSource dataSource;

    public CardDataRepository(DatastoreListener activity) {
        this.dataSource = new FirestoreCardDataSource(activity);
    }

    @Override
    public void insert(Card card) {
        dataSource.insert("cards", card);
    }

    @Override
    public void load(String id) {
        dataSource.load("cards", id);
    }

    @Override
    public void getAll() {
        dataSource.getAll("cards");
    }

    @Override
    public void getFromSearch(String query) {
        dataSource.getFromSearch("cards", query);
    }

    @Override
    public void update(String id, Card card) {
        dataSource.update("cards", id, card);
    }

    @Override
    public void delete(String id) {
        dataSource.delete("cards", id);
    }


}
