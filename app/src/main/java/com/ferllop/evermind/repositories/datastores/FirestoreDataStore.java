package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreDataStore<T extends Model> implements DataStore<T> {
    final private String TAG = "MYAPP";

    String collection;
    ModelMapper<T> mapper;
    DatastoreListener<T> listener;
    FirebaseFirestore firestore;

    public FirestoreDataStore(FirebaseFirestore firestore, String collection, ModelMapper mapper, DatastoreListener<T> listener) {
        this.firestore = firestore;
        this.mapper = mapper;
        this.listener = listener;
        this.collection = collection;
    }

    @Override
    public void insert(T item) {
        firestore.collection(collection)
                .add(mapper.execute(item))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        listener.onSave();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        listener.onError(DataStoreError.ON_INSERT);
                    }
                });
    }

    @Override
    public void load(String id){
        firestore.collection(collection).document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                listener.onLoad(mapper.execute(document.getId(), document.getData()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "failed loading card");
                listener.onError(DataStoreError.ON_LOAD);
            }
        });
    }

    @Override
    public void getAll(){
        firestore.collection(collection).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            listener.onLoad(mapper.execute(document.getId(), document.getData()));
                        }
                    } else {
                        Log.d(TAG, "failed getting all cards");
                        listener.onError(DataStoreError.ON_LOAD_ALL);
                    }
                });
    }

    @Override
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
    public void update(String id, T item) {
        firestore.collection(collection).document(id)
                .set(mapper.execute(item))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        listener.onSave();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        listener.onError(DataStoreError.ON_UPDATE);
                    }
                });
    }

    @Override
    public void delete(String id) {
        firestore.collection(collection).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        listener.onDelete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        listener.onError(DataStoreError.ON_DELETE);
                    }
                });
    }

    @Override
    public void getFromUniqueField(String field, String value) {
        firestore.collection(collection).whereEqualTo(field, value)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
}
