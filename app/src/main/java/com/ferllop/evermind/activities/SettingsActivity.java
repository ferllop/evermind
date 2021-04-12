package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.SubscriptionRepository;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.UserRepository;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.AuthMessage;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.DataStoreMessage;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;
import com.ferllop.evermind.repositories.listeners.UserDataStoreListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends MainNavigationActivity implements UserDataStoreListener, CardDataStoreListener, SubscriptionDataStoreListener {

    EditText name;
    EditText time;
    Button save;
    Button importButton;
    UserRepository userRepository;
    String newName;
    String newTime;
    String oldName;
    String oldTime;
    UserLocalDataStore userLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userRepository = new UserRepository(this);
        name = findViewById(R.id.settings_name_edit);
        time = findViewById(R.id.settings_time_edit);
        save = findViewById(R.id.settings_save_button);
        importButton = findViewById(R.id.setting_import_button);
        importButton.setVisibility(View.INVISIBLE);
        userLocal = new UserLocalDataStore(importButton.getContext());

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

        if(userRepository.isLoggedUserAdmin()) {
            importButton.setVisibility(View.VISIBLE);
            importButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    importData();
                }
            });
        }
    }

    private void importData() {
        InputStream inputStream = getResources().openRawResource(R.raw.evermind);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            while((line = bufferedReader.readLine()) != null){
                Log.d(TAG, "importData: line" + line);
                String[] tokens = line.split(",");
                String question = tokens[0];
                String answer = tokens[1];
                List<String> labelsList = new ArrayList<>();
                if(!question.isEmpty() ||
                        !answer.isEmpty() ||
                        labelsList.size() > 0
                ){
                    for(int i=2; i<tokens.length; i++){
                        labelsList.add(tokens[i].replace("\"", ""));
                    }
                    String labels = TextUtils.join(",", labelsList);
                    Log.d(TAG, "importData: before -> " + labels);
                    new CardRepository(SettingsActivity.this).insert(
                            new Card(userLocal.getID(), userLocal.getUsername(), question, answer, labels)
                    );
                }
            }
            Toast.makeText(this, R.string.cards_imported, Toast.LENGTH_SHORT);
        } catch (IOException err){
            Log.d(TAG, "importData: " + err);
        }
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
    public void onLoad(Card card) {

    }

    @Override
    public void onSave(Subscription subscription) {
        SubscriptionsGlobal.getInstance().addSubscription(subscription);
    }

    @Override
    public void onLoadAllSubscriptions(List<Subscription> subscriptions) {

    }

    @Override
    public void onLoad(Subscription item) {

    }

    @Override
    public void onDelete(String id) {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave(Card card) {
        new SubscriptionRepository((SubscriptionDataStoreListener) this)
                .subscribeUserToCard(userLocal.getID(), card.getId());

    }

    @Override
    public void onLoadAllCards(List<Card> cards) {

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