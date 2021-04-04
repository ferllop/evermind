package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.UserRepository;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SettingsActivity extends MainNavigationActivity implements UserDataStoreListener {

    EditText name;
    EditText time;
    Button save;
    UserRepository userRepository;
    String newName;
    String newTime;
    String oldName;
    String oldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userRepository = new UserRepository(this);
        name = findViewById(R.id.settings_name_edit);
        time = findViewById(R.id.settings_time_edit);
        save = findViewById(R.id.settings_save_button);

        oldName = new UserLocalDataStore(this).getName();
        name.setText(oldName);
        oldTime = new UserLocalDataStore(this).getDayStartTime();
        time.setText(oldTime);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = name.getText().toString();
                if(!newName.equals(oldName)){
                    saveNewName();
                }

                newTime = time.getText().toString();
                if(!newTime.equals(oldTime)){
                    saveNewTime();
                }
            }
        });
    }

    private void saveNewTime() {
        int time = Integer.parseInt(newTime);
        if(time < -1 || time > 23){
            Toast.makeText(this, R.string.time_restriction, Toast.LENGTH_SHORT).show();
            return;
        }
        userRepository.updateLoggedUserDayStartTime(time);
    }

    private void saveNewName() {
        if(newName.isEmpty()){
            Toast.makeText(this, R.string.name_restriction, Toast.LENGTH_SHORT).show();
            return;
        }
        userRepository.updateLoggedUserName(newName);
    }

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.profile_screen_title);
    }

    @Override
    public void onLoad(User user) {

    }

    @Override
    public void onDelete(String id) {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave(User user) {
        userRepository.setLocalUserName(newName);
        oldName = name.getText().toString();
        oldTime = time.getText().toString();
        Toast.makeText(this, R.string.settings_saved_properly, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadAll(List<User> user) {

    }

    @Override
    public void onUserDataStoreResult(DataStoreMessage message) {

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
}