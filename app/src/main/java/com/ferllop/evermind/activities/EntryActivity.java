package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.models.UserStatus;
import com.ferllop.evermind.repositories.UserRepository;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EntryActivity extends AppCompatActivity implements UserDataStoreListener {

    final String TAG = "MYAPP-Entry";
    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        userRepo = new UserRepository(this);

        if (userRepo.isUserLoggedIn()){
                 Log.d(TAG, "onCreate: extradata");
                if(!userRepo.isUserVerified()){
                    Log.d(TAG, "onCreate: no verified");
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    userRepo.getLoggedUserExtraData();
                }
        } else {
            Log.d(TAG, "onCreate: clear");
            new UserLocalDataStore(this).clear();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    public void onLoad(User user) {
        Log.d(TAG, "onLoad: " + user);
    }

    @Override
    public void onDelete(String id) {

    }

    @Override
    public void onError(DataStoreError error) {
    }

    @Override
    public void onSave(User user) {

    }

    @Override
    public void onLoadAll(List<User> users) {
        if(users.size() == 0) {
            Log.d(TAG, "onLoadAll: no users");
            userRepo.signOut(this);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        Log.d(TAG, "onLoadAll: " + users);
        User user = users.get(0);
        userRepo.updateUserStatus(user.getId(), UserStatus.LOGGED_IN);
        userRepo.updateUserLastConnection(user.getId(), Timestamp.now());
        userRepo.setCache(user);
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void usernameExists(boolean exist) {

    }

    @Override
    public void emailExists(boolean exist) {

    }

    @Override
    public void onAuthActionResult(AuthMessage message) {

    }

    @Override
    public void onUserDataStoreResult(DataStoreMessage message) {

    }
}