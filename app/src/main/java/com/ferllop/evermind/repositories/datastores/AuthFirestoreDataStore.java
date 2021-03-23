package com.ferllop.evermind.repositories.datastores;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ferllop.evermind.repositories.fields.AuthError;
import com.ferllop.evermind.repositories.listeners.AuthListener;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthFirestoreDataStore implements AuthDataStore {

    final String TAG = "MYAPP-auth";
    private FirebaseAuth auth;
    private AuthListener listener;

    public AuthFirestoreDataStore(FirebaseAuth auth, AuthListener listener){
        this.auth = auth;
        this.listener = listener;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            listener.onRegister(user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            listener.onError(AuthError.ON_REGISTER);
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            listener.onLogin(user.getUid());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            listener.onError(AuthError.ON_LOGIN);
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }

    @Override
    public void logout() {
        auth.signOut();
    }

    @Override
    public void sendVerificationEmailToCurrentUser() {
        FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    @Override
    public boolean isUserVerified(){
        return auth.getCurrentUser().isEmailVerified();
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Reset password email sent.");
                            listener.onAuthActionResult(AuthMessage.RESET_PASSWORD_EMAIL_SENT);
                        } else {
                            Log.d(TAG, "Reset password email not sent.");
                            listener.onAuthActionResult(AuthMessage.RESET_PASSWORD_EMAIL_NOT_SENT);
                        }
                    }
                });
    }
}
