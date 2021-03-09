package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.Model;

public interface DataStore<T extends Model> {  void insert(T card);
    void load(String id);
    void getAll();
    void getFromSearch(String query);
    void update(String id, T item);
    void delete(String id);
    void getFromUniqueField(String field, String value);
}
