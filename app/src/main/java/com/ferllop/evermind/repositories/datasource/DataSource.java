package com.ferllop.evermind.repositories.datasource;


public interface DataSource<T> {
    void insert(String collection, T item);
    void load(String collection, String id);
    void getAll(String collection);
    void getFromSearch(String collection, String query);
    void update(String collection, String id, T item);
    void delete(String collection, String id);
}
