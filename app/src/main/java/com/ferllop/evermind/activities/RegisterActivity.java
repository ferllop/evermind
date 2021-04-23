package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.VerifyEmailDialog;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.UserRepository;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;

import java.util.List;

public class RegisterActivity extends AppCompatActivity
        implements UserDataStoreListener, VerifyEmailDialog.VerifyEmailDialogListener {

    final String TAG = "MYAPP-registeract";
    EditText name;
    EditText username;
    EditText email;
    EditText password;
    EditText repeatPassword;
    Button registerButton;
    ProgressBar registerInProgress;
    UserRepository userRepository;
    boolean usernameExists = true;
    boolean emailExists = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRepository = new UserRepository(this);
        name = findViewById(R.id.register_name_TextEdit);
        username = findViewById(R.id.register_username_TextEdit);
        email = findViewById(R.id.register_email_textEdit);
        password = findViewById(R.id.register_password_TextEdit);
        repeatPassword = findViewById(R.id.register_repeat_password_TextEdit);
        registerButton = findViewById(R.id.register_register_Button);
        registerInProgress = findViewById(R.id.register_progress_loader);

        Intent intent = getIntent();
        if (intent.hasExtra("email")){
            email.setText(intent.getStringExtra("email"));
        }
        if (intent.hasExtra("password")){
            password.setText(intent.getStringExtra("password"));
        }

        findViewById(R.id.register_register_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainPass = password.getText().toString();
                String repeatPass = repeatPassword.getText().toString();
                if (name.getText().toString().isEmpty() ||
                        username.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() ||
                        mainPass.isEmpty() ||
                        repeatPass.isEmpty()
                ) {
                    Toast.makeText(RegisterActivity.this, R.string.all_fields_mandatory, Toast.LENGTH_SHORT).show();
                } else if(!mainPass.equals(repeatPass)) {
                    Toast.makeText(RegisterActivity.this, R.string.passwords_not_equals, Toast.LENGTH_SHORT).show();
                } else {
                    registerUser();
                }
            }
        });
    }

    private void showRegisterInProgress(){
        registerButton.setVisibility(View.INVISIBLE);
        registerInProgress.setVisibility(View.VISIBLE);
    }

    private void hideRegisterInProgress(){
        registerButton.setVisibility(View.VISIBLE);
        registerInProgress.setVisibility(View.INVISIBLE);
    }

    private void registerUser() {
        if (userRepository.isValidPassword(password.getText().toString())) {
            showRegisterInProgress();
            String rawName = name.getText().toString();
            String nameWithUpperInitial = rawName.toUpperCase().charAt(0) + rawName.substring(1);
            userRepository.registerUser(
                    nameWithUpperInitial,
                    username.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(), !usernameExists && !emailExists
            );
        } else {
            notValidPasswordToast();
        }
    }

    private void notValidPasswordToast(){
        hideRegisterInProgress();
        Toast.makeText(this, R.string.password_not_valid, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoad(User user) {
        startActivity(new Intent(this, EntryActivity.class));
    }

    @Override
    public void onDelete(String id) {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave(User user) {
        DialogFragment dialog = new VerifyEmailDialog(getString(R.string.register_verify_email_sent));
        dialog.show(getSupportFragmentManager(), "VerifyEmailDialogFragment");
    }

    @Override
    public void onLoadAll(List<User> user) {

    }

    @Override
    public void usernameExists(boolean exist) {
        usernameExists = exist;
        if(usernameExists){
            hideRegisterInProgress();
            if (userRepository.isUserLoggedIn() && !userRepository.isUserVerified()) {
                DialogFragment dialog = new VerifyEmailDialog(getString(R.string.register_remember_verify_email_sent));
                dialog.show(getSupportFragmentManager(), "VerifyEmailDialogFragment");
            } else {
                Toast.makeText(this, R.string.username_must_unique, Toast.LENGTH_LONG).show();
            }
        } else {
            if(!emailExists){
                registerUser();
            }
        }
    }

    @Override
    public void emailExists(boolean exist) {
        emailExists = exist;
        if(emailExists){
            hideRegisterInProgress();
            if (userRepository.isUserLoggedIn() && !userRepository.isUserVerified()) {
                DialogFragment dialog = new VerifyEmailDialog(getString(R.string.register_remember_verify_email_sent));
                dialog.show(getSupportFragmentManager(), "VerifyEmailDialogFragment");
            } else {
                Toast.makeText(this, R.string.email_must_unique, Toast.LENGTH_LONG).show();
            }
        } else {
            if(!usernameExists){
                registerUser();
            }
        }
    }

    @Override
    public void onAuthActionResult(AuthMessage message) {

    }

    @Override
    public void onOk(DialogFragment dialog) {
        hideRegisterInProgress();
        startActivity(new Intent(this, EntryActivity.class));
    }
}