package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.UserRepository;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements UserDataStoreListener {

    private final String TAG = "MYAPP-LoginActivity";
    UserRepository userRepository;
    UserLocalDataStore userLocal;
    TextView forgotPassword;
    Button loginButton;
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        EditText emailField = findViewById(R.id.login_email_textEdit);
        EditText passwordField = findViewById(R.id.login_password_TextEdit);
        forgotPassword = findViewById(R.id.login_forgot_password_link);
        loginButton = findViewById(R.id.login_login_Button);
        loginProgress = findViewById(R.id.login_progress_loader);

        userLocal = new UserLocalDataStore(emailField.getContext());

        findViewById(R.id.login_login_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailField.getText().toString().isEmpty() ||
                        passwordField.getText().toString().isEmpty()){
                    return;
                }
                if (userRepository.isUserLoggedIn() && !userRepository.isUserVerified()){
                    Log.d(TAG, "aquiiiionCreate: " + userRepository.isUserLoggedIn() + " " + userRepository.isUserVerified());
                    Toast.makeText(LoginActivity.this, R.string.must_verify_message, Toast.LENGTH_LONG).show();
                } else {
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();
                    showLoginProgress();
                    userRepository.login(email, password);
                    Log.d(TAG, "onClick: ");
                }
            }
        });

        findViewById(R.id.login_register_Link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();
                    if(!email.isEmpty()){
                        intent.putExtra("email", emailField.getText().toString());
                    }
                    if(!password.isEmpty()){
                        intent.putExtra("password", passwordField.getText().toString());
                    }
                    startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailField.getText().toString().isEmpty()) {
                    return;
                }

                userRepository.sendResetPasswordEmail(emailField.getText().toString());
                forgotPassword.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showLoginProgress(){
        Log.d(TAG, "showLoginProgress: ");
        loginButton.setVisibility(View.INVISIBLE);
        loginProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoginProgress(){
        Log.d(TAG, "hideLoginProgress: ");
        loginButton.setVisibility(View.VISIBLE);
        loginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            Log.d(TAG, "onStart: " + currentUser.getEmail());
        }
        //updateUI(currentUser);
    }

    @Override
    public void onLoad(User user) {

    }

    @Override
    public void onDelete(String id) {

    }

    @Override
    public void onError(DataStoreError error) {
        if (error == DataStoreError.ON_LOAD){
            Toast.makeText(getApplicationContext(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
            hideLoginProgress();
        }
    }

    @Override
    public void onSave(User user) {

    }

    @Override
    public void onLoadAll(List<User> user) {
        userRepository.setCache(user.get(0));
        userRepository.loginStatusUser();
        hideLoginProgress();
        startActivity(new Intent(this, EntryActivity.class));
    }

    @Override
    public void usernameExists(boolean exist) {

    }

    @Override
    public void emailExists(boolean exist) {

    }

    @Override
    public void onAuthActionResult(AuthMessage message) {
        switch(message){
            case RESET_PASSWORD_EMAIL_SENT:
            case RESET_PASSWORD_EMAIL_NOT_SENT:
                Toast.makeText(getApplicationContext(), R.string.reset_password_sent, Toast.LENGTH_LONG).show();
                forgotPassword.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onUserDataStoreResult(DataStoreMessage message) {

    }
}