package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CrudFirestoreDataStore<T extends Model> {
    final private String TAG = "MYAPP";

    String collection;
    ModelMapper<T> mapper;
    FirebaseFirestore firestore;
    CrudDataStoreListener<T> listener;

    public CrudFirestoreDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper<T> mapper,
            CrudDataStoreListener<T> listener
    ) {
        this.firestore = firestore;
        this.mapper = mapper;
        this.collection = collection;
        this.listener = listener;
    }

    public void insert(T item) {
        firestore.collection(collection)
                .add(mapper.execute(item))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        item.setId(documentReference.getId());
                        listener.onSave(item);
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

    public void update(String id, T item) {
        firestore.collection(collection).document(id)
                .set(mapper.execute(item))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        listener.onSave(item);
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

    public void delete(String id) {
        firestore.collection(collection).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        listener.onDelete(id);
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

    public void getFromUniqueField(String field, String value) {
        firestore.collection(collection).whereEqualTo(field, value)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "getAll: " + task.getResult().size());
                    List<T> items = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        items.add(mapper.execute(document.getId(), document.getData()));
                    }
                    listener.onLoadAll(items);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onError(DataStoreError.ON_SEARCH);
                }
            }
        });
    }
}
