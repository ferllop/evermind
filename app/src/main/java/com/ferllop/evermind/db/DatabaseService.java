package com.ferllop.evermind.db;

import com.ferllop.evermind.CardsAdapter;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Model;

public interface DatabaseService<T extends Model> {
    void insert(String collection, T model);
    void load(String collection, String id);
    void getAll(String collection);
    void getFromSearch(String collection, String query);
    void update(String collection, String id, T model);
    void delete(String collection, String id);

}
