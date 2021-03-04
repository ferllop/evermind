package com.ferllop.evermind.db;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Model;

public class DbController<T extends Model> {
    final private String TAG = "dbController";

    DatabaseService db;
    String collection;

    public DbController(String collection, Class<T> typeParameterClass, DbUser dbUser){
        this.collection = collection;
        this.db = new FirestoreService<T>(typeParameterClass, dbUser);
    }


    public void insertCard(T model) {
        db.insert(collection, model);
    }

    public void getCard(String id){
        db.load(collection, id);
    }

    public void getAllCards(){
        db.getAll(collection);
    }

    public void getCards(String query) {
        db.getFromSearch(collection, query);
    }

    public void updateCard(String id, Card card) {
        db.update(collection, id, card);
    }

    public void deleteCard(String id) {
        db.delete(collection, id);
    }
}


