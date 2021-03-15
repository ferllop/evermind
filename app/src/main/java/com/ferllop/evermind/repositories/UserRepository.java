package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.datastores.UserFirestoreDataStore;
import com.ferllop.evermind.repositories.fields.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.mappers.UserMapper;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserRepository {
    final String TAG = "MYAPP-UserRepo";

    UserFirestoreDataStore dataStore;

    public UserRepository(CrudDataStoreListener<User> listener) {
        this.dataStore = new UserFirestoreDataStore(
                FirebaseFirestore.getInstance(),
                FirestoreCollectionsLabels.USER.getValue(),
                new UserMapper(),
                listener
        );
    }

    public void insert(User user) {
        dataStore.insert(user);
    }

    public void load(String id) {
        dataStore.load( id);
    }

    public void getAll() {
        dataStore.getAll();
    }

    public void update(String id, User user) {
        dataStore.update(id, user);
    }

    public void delete(String id) {
        dataStore.delete(id);
    }

    public void getFromUniqueField(String field, String value){
        dataStore.getFromUniqueField(field, value);
    }
}
