package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.fields.SubscriptionField;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreSubscriptionDataStore extends FirestoreDataStore<Subscription> {
    final String TAG = "MYAPP-Subscription";

    SubscriptionListener subscriptionListener;
    public FirestoreSubscriptionDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper mapper,
            DatastoreListener<Subscription> subListener,
            SubscriptionListener subscriptionListener) {
        super(firestore, collection, mapper, subListener);
        this.subscriptionListener = subscriptionListener;
    }

    public void getCardFromUserToReviewFromDate(String userID, Timestamp date){
        Query dbQuery = firestore.collection(collection);
        dbQuery = dbQuery.whereEqualTo(SubscriptionField.USER_ID.getValue(), userID);
        Log.d(TAG, SubscriptionField.NEXT_REVIEW.getValue());
        Log.d(TAG, date.toString());
        dbQuery = dbQuery.whereLessThanOrEqualTo(SubscriptionField.NEXT_REVIEW.getValue(), date);

        dbQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    subscriptionListener.onCount(task.getResult().size());
                    List<Subscription> subs = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        subs.add(mapper.execute(document.getId(), document.getData()));
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
}
