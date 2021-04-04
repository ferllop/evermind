package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.models.UserStatus;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.fields.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.fields.UserField;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserFirestoreDataStore extends FirestoreDataStore<User> implements CrudDataStoreListener<User>{

    final String TAG = "MYAPP-UserFirestoreDtSt";

    UserDataStoreListener listener;

    public UserFirestoreDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper<User> mapper,
            UserDataStoreListener listener
    ) {
        super(firestore, collection, mapper);
        this.listener = listener;
    }

    @Override
    protected CrudDataStoreListener<User> getCrudListener() {
        return this;
    }

    @Override
    public void onLoad(User user) {
        listener.onLoad(user);
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
    public void onSave(User user) {
        listener.onSave(user);
    }

    @Override
    public void onLoadAll(List<User> users) { listener.onLoadAll(users);}

    @Override
    public void onUserDataStoreResult(DataStoreMessage message) {

    }

    public void existUsername(String username){
        firestore.collection(FirestoreCollectionsLabels.USER.getValue())
                .whereEqualTo(UserField.USERNAME.getValue(), username.toLowerCase())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().size() < 1){
                        listener.usernameExists(false);
                    } else {
                        listener.usernameExists(true);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onError(DataStoreError.ON_SEARCH);
                }
            }
        });
    }

    public void existEmail(String email){
        firestore.collection(FirestoreCollectionsLabels.USER.getValue())
                .whereEqualTo(UserField.EMAIL.getValue(), email.toLowerCase())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().size() < 1){
                        listener.emailExists(false);
                    } else {
                        listener.emailExists(true);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onError(DataStoreError.ON_SEARCH);
                }
            }
        });
    }

    public void updateUserStatus(String userID, UserStatus newStatus){
        firestore.collection(collection).document(userID)
                .update(UserField.STATUS.getValue(), newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);

                    }
                });
    }

    public void updateUserLastConnection(String userID, Timestamp timestamp){
        firestore.collection(collection).document(userID)
                .update(UserField.LAST_CONNECTION.getValue(), timestamp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);

                    }
                });
    }

    public void updateUserLastLogin(String userID, Timestamp timestamp){
        firestore.collection(collection).document(userID)
                .update(UserField.LAST_LOGIN.getValue(), timestamp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void updateUserName(String userID, String newName){
        firestore.collection(collection).document(userID)
                .update(UserField.NAME.getValue(), newName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Name successfully updated!");
                        listener.onSave(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating the name of the document", e);
                    }
                });
    }

    public void updateDayStartTime(String userID, int newTime){
        firestore.collection(collection).document(userID)
                .update(UserField.DAY_START_TIME.getValue(), newTime)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Day start time successfully updated!");
                        listener.onSave(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating the name of the document", e);
                    }
                });
    }
}
