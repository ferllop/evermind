package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.datastores.Search;

public interface CardRepository {
    void insert(Card card);
    void load(String id);
    void getAll();
    void getFromSearch(String query);
    void update(String id, Card card);
    void delete(String id);

}
