package com.ferllop.evermind.repositories.datastores.technologies;

import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;

public abstract class DataStore<T extends Model> {

    ModelMapper<T> mapper;
    DatastoreListener<T> listener;

    public DataStore(ModelMapper<T> mapper, DatastoreListener<T> listener) {
        this.mapper = mapper;
        this.listener = listener;
    }

    public abstract void insert(T card);
    public abstract void load(String id);
    public abstract void getAll();
    public abstract  void getFromSearch(String query);
    public abstract  void update(String id, T item);
    public abstract  void delete(String id);
}
