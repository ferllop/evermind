package com.ferllop.evermind.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.CardsAdapter;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.IdentifiedCard;
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

public class DbController {
    final private String TAG = "dbController";
    DbUser dbUser;

    public DbController(DbUser dbUser){
        this.dbUser = dbUser;
    }

    public void insertCard(Card card) {
        FirebaseFirestore.getInstance().collection("cards")
                .add(card)
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

    public void loadCard(String id){
        FirebaseFirestore.getInstance().collection("cards").document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dbUser.onLoad(new IdentifiedCard(id, documentSnapshot.toObject(Card.class)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("el tag", "fallaaaa");
                dbUser.onError("falla");
            }
        });
    }

    public void getAllCards(CardsAdapter adapter){
        FirebaseFirestore.getInstance().collection("cards").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            dbUser.onLoad(new IdentifiedCard(document.getId(), document.toObject(Card.class)));
                        }
                    } else {
                        Log.d(TAG, "failed getting all cards");
                        dbUser.onError("failed getting all cards");
                    }
                });
    }

    public void getCards(String query, CardsAdapter adapter) {
        Search search = new Search(query);

        Query dbQuery = FirebaseFirestore.getInstance().collection("cards");
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
                        dbUser.onLoad(new IdentifiedCard(document.getId(), document.toObject(Card.class)));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    dbUser.onError("Error getting documents: ");
                }
            }
        });
    }

    public void updateCard(String id, Card card) {
        FirebaseFirestore.getInstance().collection("cards").document(id)
                .set(card)
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

    public void deleteCard(String id) {
        FirebaseFirestore.getInstance().collection("cards").document(id)
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


