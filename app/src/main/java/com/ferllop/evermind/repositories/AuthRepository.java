package com.ferllop.evermind.repositories;

import com.ferllop.evermind.repositories.datastores.AuthDataStore;
import com.ferllop.evermind.repositories.datastores.AuthFirestoreDataStore;
import com.ferllop.evermind.repositories.listeners.AuthListener;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    AuthDataStore auth;

    public AuthRepository(AuthListener listener){
        auth = new AuthFirestoreDataStore(FirebaseAuth.getInstance(), listener);
    }

    public void register(String email, String password ){
        auth.register(email, password);
    }

    public void login(String email, String password){
        auth.login(email, password);
    }

    public void logout(){
        auth.logout();
    }

    public void sendVerificationEmailToCurrentUser(){
        auth.sendVerificationEmailToCurrentUser();
    }

    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public boolean isUserVerified() {
        return auth.isUserVerified();
    }

    public String getUserUID(){
        return FirebaseAuth.getInstance().getUid();
    }

    public String getUserEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void sendResetPasswordEmail(String email) {
        auth.sendResetPasswordEmail(email);
    }
}
