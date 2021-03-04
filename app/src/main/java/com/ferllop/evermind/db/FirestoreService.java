package com.ferllop.evermind.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.Model;
import com.ferllop.evermind.models.Search;
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

public class FirestoreService<T extends Model> implements DatabaseService<T> {
    final private String TAG = "Firestore Service";

    Class<T> typeParameterClass;
    FirebaseFirestore db;
    DbUser dbUser;

    public FirestoreService(Class<T> typeParameterClass, DbUser dbUser) {
        this.typeParameterClass = typeParameterClass;
        this.db = FirebaseFirestore.getInstance();
        this.dbUser = dbUser;
    }

    @Override
    public void insert(String collection, T model) {
        db.collection(collection)
                .add(model)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        dbUser.onSave();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        dbUser.onError("Error adding document");
                    }
                });
    }

    @Override
    public void load(String collection, String id){
        db.collection(collection).document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dbUser.onLoad(new ModelDao(id, documentSnapshot.toObject(typeParameterClass)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("el tag", "fallaaaa");
                dbUser.onError("falla");
            }
        });
    }

    @Override
    public void getAll(String collection){
        db.collection(collection).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            dbUser.onLoad(new ModelDao(document.getId(), document.toObject(typeParameterClass)));
                        }
                    } else {
                        Log.d(TAG, "failed getting all cards");
                        dbUser.onError("failed getting all cards");
                    }
                });
    }

    @Override
    public void getFromSearch(String collection, String query) {
        Search search = new Search(query);

        Query dbQuery = db.collection(collection);
        if(search.hasLabels()){
            for(String label: search.getLabels()){
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
                        dbUser.onLoad(new ModelDao(document.getId(), document.toObject(typeParameterClass)));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    dbUser.onError("Error getting documents: ");
                }
            }
        });
    }

    @Override
    public void update(String collection, String id, Model model) {
        db.collection(collection).document(id)
                .set(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        dbUser.onSave();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        dbUser.onError("Error updating document");
                    }
                });
    }

    @Override
    public void delete(String collection, String id) {
        db.collection(collection).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        dbUser.onDelete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        dbUser.onError("Error deleting document");
                    }
                });
    }

}
