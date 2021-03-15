package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class FirestoreDataStore<T extends Model> {
    String collection;
    ModelMapper<T> mapper;
    FirebaseFirestore firestore;
    CrudFirestoreDataStore<T> crud;

    public FirestoreDataStore(FirebaseFirestore firestore, String collection, ModelMapper<T> mapper) {
        this.firestore = firestore;
        this.mapper = mapper;
        this.collection = collection;
        crud = new CrudFirestoreDataStore<T>(firestore, collection, mapper, getCrudListener());
    }

    public void insert(T item) {
        crud.insert(item);
    }

    public void load(String id) {
        crud.load( id);
    }

    public void getAll() {
        crud.getAll();
    }

    public void update(String id, T item) {
        crud.update(id, item);
    }

    public void delete(String id) {
        crud.delete(id);
    }

    public void getFromUniqueField(String field, String value){
        crud.getFromUniqueField(field, value);
    }
    protected abstract CrudDataStoreListener<T> getCrudListener();
}
