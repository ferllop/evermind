package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Level;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.fields.SubscriptionField;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SubscriptionFirestoreDataStore extends FirestoreDataStore<Subscription> implements CrudDataStoreListener<Subscription>{
    final String TAG = "MYAPP-Subscription";

    SubscriptionDataStoreListener listener;

    public SubscriptionFirestoreDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper<Subscription> mapper,
            SubscriptionDataStoreListener listener) {
        super(firestore, collection, mapper);
        this.listener = listener;
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

    @Override
    protected CrudDataStoreListener<Subscription> getCrudListener() {
        return this;
    }

    @Override
    public void onLoad(Subscription item) {
        listener.onLoad(item);
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
    public void onSave(Subscription item) {
        listener.onSave(item);
    }

    @Override
    public void onNotFound() {
        listener.onNotFound();
    }

    @Override
    public void onLoadAll(List<Subscription> subs) {
        listener.onLoadAll(subs);
    }
}
