package com.ferllop.evermind.repositories.datastores;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.ModelMapper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserFirestoreDataStore extends FirestoreDataStore<User> implements CrudDataStoreListener<User>{

    final String TAG = "MYAPP-UserFirestoreDtSt";

    UserDataStoreListener listener;

    public UserFirestoreDataStore(
            FirebaseFirestore firestore,
            String collection,
            ModelMapper<User> mapper,
            CrudDataStoreListener<User> listener
    ) {
        super(firestore, collection, mapper);
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
    public void onNotFound() {
        listener.onNotFound();
    }

    @Override
    public void onLoadAll(List<User> items) {

    }

}
