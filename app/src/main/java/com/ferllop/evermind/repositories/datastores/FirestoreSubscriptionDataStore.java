package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.fields.SubscriptionField;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.ferllop.evermind.repositories.mappers.SubscriptionMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreSubscriptionDataStore{
    final String TAG = "MYAPP-Subscription";

    String collection;
    FirebaseFirestore firestore;
    SubscriptionListener subscriptionListener;

    public FirestoreSubscriptionDataStore(FirebaseFirestore firestore, SubscriptionListener subscriptionListener) {
        this.subscriptionListener = subscriptionListener;
        this.collection = FirestoreCollectionsLabels.SUBSCRIPTION.getValue();
        this.firestore = firestore;
    }

    public void insert(Subscription subscription) {
        firestore.collection(collection)
                .add(new SubscriptionMapper().execute(subscription))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        subscription.setId(documentReference.getId());
                        subscriptionListener.onSave(subscription);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        subscriptionListener.onError(DataStoreError.ON_INSERT);
                    }
                });
    }

    public void getCardFromUserToReviewFromDate(String userID, Timestamp date){
        Query dbQuery = firestore.collection(collection);
        dbQuery = dbQuery.whereEqualTo(SubscriptionField.USER_ID.getValue(), userID);
        dbQuery = dbQuery.whereLessThanOrEqualTo(SubscriptionField.NEXT_REVIEW.getValue(), date);

        dbQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Subscription> subs = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        subs.add(new SubscriptionMapper().execute(document.getId(), document.getData()));
                    }
                    subscriptionListener.onLoad(subs);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    //listener.onError(DataStoreError.ON_SEARCH);
                }
            }
        });
    }

    public void updateLevel(String id, Level level, Timestamp nextReview) {
        firestore.collection(collection).document(id)
                .update(
                        SubscriptionField.LEVEL.getValue(), level.name(),
                        SubscriptionField.NEXT_REVIEW.getValue(), nextReview,
                        SubscriptionField.LAST_REVIEW.getValue(), Timestamp.now()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        //listener.onSave();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        //listener.onError(DataStoreError.ON_UPDATE);
                    }
                });
    }

    public void getFromUniqueField(String field, String value) {
        firestore.collection(collection).whereEqualTo(field, value).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Subscription> subs = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        subs.add(new SubscriptionMapper().execute(document.getId(), document.getData()));
        Log.d(TAG, "getFromUniqueField: " + field + " -- " + value);
                    }
                    subscriptionListener.onLoad(subs);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    //listener.onError(DataStoreError.ON_SEARCH);
                }
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
                        subscriptionListener.onDelete(id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        subscriptionListener.onError(DataStoreError.ON_DELETE);
                    }
                });
    }
}
