package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.datastores.DataStore;
import com.ferllop.evermind.repositories.datastores.FirestoreCollectionsLabels;
import com.ferllop.evermind.repositories.datastores.FirestoreDataStore;
import com.ferllop.evermind.repositories.mappers.UserMapper;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserFirestoreRepository implements UserRepository {

        DataStore<User> dataStore;

        public UserFirestoreRepository(DatastoreListener<User> listener) {
            this.dataStore = new FirestoreDataStore<>(
                    FirebaseFirestore.getInstance(),
                    FirestoreCollectionsLabels.USER.getValue(),
                    new UserMapper(),
                    listener
            );
        }

        @Override
        public void insert(User user) {
            dataStore.insert(user);
        }

        @Override
        public void load(String id) {
            dataStore.load( id);
        }

        @Override
        public void getAll() {
            dataStore.getAll();
        }

        @Override
        public void update(String id, User user) {
            dataStore.update(id, user);
        }

        @Override
        public void delete(String id) {
            dataStore.delete(id);
        }

        @Override
        public void getFromUniqueField(String field, String value){
            dataStore.getFromUniqueField(field, value);
        }

}
