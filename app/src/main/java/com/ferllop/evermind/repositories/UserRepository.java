package com.ferllop.evermind.repositories;

import android.app.Activity;
import android.content.Context;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.models.UserStatus;
import com.ferllop.evermind.repositories.datastores.UserFirestoreDataStore;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.AuthError;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.fields.CollectionsLabels;
import com.ferllop.evermind.repositories.fields.UserField;
import com.ferllop.evermind.repositories.listeners.AuthListener;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;
import com.ferllop.evermind.repositories.mappers.UserMapper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserRepository implements AuthListener {
    final String TAG = "MYAPP-UserRepo";

    UserLocalDataStore localDataStore;
    UserFirestoreDataStore dataStore;
    AuthRepository authRepository;
    RegisteringUser registeringUser;
    UserDataStoreListener listener;

    public UserRepository(UserDataStoreListener listener) {
        this.dataStore = new UserFirestoreDataStore(
                FirebaseFirestore.getInstance(),
                CollectionsLabels.USER.getValue(),
                new UserMapper(),
                listener
        );
        this.authRepository = new AuthRepository(this);
        this.listener = listener;
        this.localDataStore = new UserLocalDataStore((Activity) listener);

    }

    public void insert(User user) {
        dataStore.insert(user);
    }

    public void load(String id) {
        dataStore.load(id);
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

    public void getLoggedUserExtraData(){
        dataStore.getFromUniqueField(UserField.AUTH_ID.getValue(), authRepository.getUserUID());
    }

    public void registerUser(String name, String username, String email, String password, boolean validated){
        if (validated){
            authRepository.register(email, password);
            registeringUser = new RegisteringUser().setName(name).setUsername(username).setEmail(email);
        } else {
            dataStore.existUsername(username);
            dataStore.existEmail(email);
        }
    }

    public void clearCache(){
        localDataStore.clear();
    }

    public void setCache(User user){
        localDataStore.setUser(user);
    }

    public void login(String email, String password){
        authRepository.login(email, password);
    }

    public static boolean isValidPassword(String password){
        return password.matches(".{8,32}")
                && password.matches(".*[a-z]+.*")
                && password.matches(".*[A-Z]+.*")
                && password.matches(".*[-_:,;:<.>+@#|%&/()=?¿¡!]+.*");
    }

    public boolean isUserVerified(){
        return authRepository.isUserVerified();
    }
    public boolean isUserLoggedIn(){
        return authRepository.isLoggedIn();
    }

    @Override
    public void onRegister(String uid) {
        authRepository.sendVerificationEmailToCurrentUser();
        User user = registeringUser.build(uid, UserStatus.VERIFICATION_EMAIL, Timestamp.now());
        insert(user);
    }

    @Override
    public void onLogin(String uid) {
        getFromUniqueField(UserField.AUTH_ID.getValue(), uid);
    }


    @Override
    public void onError(AuthError error) {
        listener.onError(DataStoreError.ON_LOAD);
    }


    @Override
    public void onAuthActionResult(AuthMessage message) {
        switch(message){
            case RESET_PASSWORD_EMAIL_SENT:
            case RESET_PASSWORD_EMAIL_NOT_SENT:
                listener.onAuthActionResult(message);
                break;
        }
    }

    public void signOut(Context context) {
        UserLocalDataStore localDataStore = new UserLocalDataStore(context);
        if(localDataStore.getID() != null){
            updateUserStatus(localDataStore.getID(), UserStatus.LOGGED_OUT);
            localDataStore.clear();
        }
        authRepository.signOut();
    }

    public void loginStatusUser(){
        String userID = localDataStore.getID();
        updateUserStatus(userID, UserStatus.LOGGED_IN);
        updateUserLastLogin(userID, Timestamp.now());
    }

    public void updateUserStatus(String userID, UserStatus newStatus){
        dataStore.updateUserStatus(userID, newStatus);
    }

    public void updateUserLastConnection(String userID, Timestamp timestamp){
        dataStore.updateUserLastConnection(userID, timestamp);
    }

    public void updateUserLastLogin(String userID, Timestamp timestamp){
        dataStore.updateUserLastLogin(userID, timestamp);
    }

    public void updateLoggedUserName(String newName){
        dataStore.updateUserName(localDataStore.getID(), newName);
    }

    public void sendResetPasswordEmail(String email) {
        authRepository.sendResetPasswordEmail(email);
    }

    public void setLocalUserName(String newName){
        localDataStore.setName(newName);
    }

    public void updateLoggedUserDayStartTime(int newTime){
        dataStore.updateDayStartTime(localDataStore.getID(), newTime);
    }

    public boolean isLoggedUserAdmin(){
        return localDataStore.getUsername().equals("evermind");
    }


    public class RegisteringUser {
        String name;
        String username;
        String email;

        public RegisteringUser setName(String name) {
            this.name = name;
            return this;
        }

        public RegisteringUser setUsername(String username) {
            this.username = username;
            return this;
        }

        public RegisteringUser setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build(String id, UserStatus status, Timestamp signIn){
            return new User(id, name, username, email, status, null, null, signIn, 0);
        }
    }
}
