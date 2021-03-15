package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CardFirestoreDataStore extends FirestoreDataStore<Card> implements CrudDataStoreListener<Card> {

    final String TAG = "MYAPP-CardFirestoreDtSt";

    CardDataStoreListener listener;

    public CardFirestoreDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper<Card> mapper,
            CardDataStoreListener listener
    ) {
        super(firestore, collection, mapper);
        this.listener = listener;
    }

    public void getFromSearch(String query) {
        Search search = new Search(query);
        Query dbQuery = firestore.collection(collection);
        if(search.hasLabels()){
            for(String label: search.getLabels()){
                dbQuery = dbQuery.whereEqualTo(CardField.LABELLING.getValue()+"."+label, true);
            }
        }
        if(search.hasAuthor()){
            dbQuery = dbQuery.whereEqualTo(CardField.AUTHOR_USERNAME.getValue(), search.getAuthorUsername());
        }

        dbQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listener.onLoad(mapper.execute(document.getId(), document.getData()));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onError(DataStoreError.ON_SEARCH);
                }
            }
        });
    }

    @Override
    public void onLoad(Card card) {
        listener.onLoad(card);
    }

    @Override
    public void onDelete(String id) {
        listener.onDelete(id);
    }

    @Override
    public void onError(DataStoreError error) {
        listener.onError(error);
    }

    @Override
    public void onSave(Card card) {
        listener.onSave(card);
    }

    @Override
    public void onNotFound() {
        listener.onNotFound();
    }

    @Override
    public void onLoadAll(List<Card> items) {

    }

    @Override
    protected CrudDataStoreListener<Card> getCrudListener() {
        return this;
    }
}
