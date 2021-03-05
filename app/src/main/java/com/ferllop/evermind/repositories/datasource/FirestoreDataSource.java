package com.ferllop.evermind.repositories.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.models.Search;
import com.ferllop.evermind.repositories.DatastoreListener;
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

import java.util.HashMap;
import java.util.Map;

public abstract class FirestoreDataSource<T extends Model> implements DataSource<T> {
    final private String TAG = "Firestore Service";

    Class<T> typeParameterClass;
    DatastoreListener<T> listener;

    public FirestoreDataSource(Class<T> typeParameterClass, DatastoreListener<T> listener) {
        this.typeParameterClass = typeParameterClass;
        this.listener = listener;
    }

    protected abstract T fromMap(String id, Map map);
    protected abstract Map<String, Object> toMap(T model);

    @Override
    public void insert(String collection, T item) {
        FirebaseFirestore.getInstance().collection(collection)
                .add(toMap(item))
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
                        listener.onError("Error adding document");
                    }
                });
    }

    @Override
    public void load(String collection, String id){
        FirebaseFirestore.getInstance().collection(collection).document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                Map<String, Object> identifiedResult = new HashMap<>();
                identifiedResult.put("data", document.getData());
                identifiedResult.put("id", document.getId());
                listener.onLoad(fromMap(document.getId(), document.getData()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("el tag", "fallaaaa");
                listener.onError("falla");
            }
        });
    }


    @Override
    public void getAll(String collection){
        FirebaseFirestore.getInstance().collection(collection).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            listener.onLoad(fromMap(document.getId(), document.getData()));
                        }
                    } else {
                        Log.d(TAG, "failed getting all cards");
                        listener.onError("failed getting all cards");
                    }
                });
    }

    @Override
    public void getFromSearch(String collection, String query) {
        Search search = new Search(query);
        Log.d(TAG, "search=> " + query);
        Query dbQuery = FirebaseFirestore.getInstance().collection(collection);
        if(search.hasLabels()){
            for(String label: search.getLabels()){
                Log.d(TAG, "label=> " + label);
                dbQuery = dbQuery.whereEqualTo("labels."+label, true);
            }
        }
        if(search.hasAuthor()){
            dbQuery = dbQuery.whereEqualTo("author", search.getAuthor());
        }

        dbQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listener.onLoad(fromMap(document.getId(), document.getData()));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onError("Error getting documents: ");
                }
            }
        });
    }

    @Override
    public void update(String collection, String id, T item) {
        FirebaseFirestore.getInstance().collection(collection).document(id)
                .set(toMap(item))
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
                        listener.onError("Error updating document");
                    }
                });
    }

    @Override
    public void delete(String collection, String id) {
        FirebaseFirestore.getInstance().collection(collection).document(id)
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
                        listener.onError("Error deleting document");
                    }
                });
    }



}
