package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.DeleteCardDialogFragment;
import com.ferllop.evermind.controllers.CardController;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Labelling;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.SubscriptionRepository;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDataActivity extends MainNavigationActivity implements
        CardDataStoreListener, SubscriptionDataStoreListener, DeleteCardDialogFragment.DeleteDialogListener {
    final private String TAG = "MYAPP-CardDataActivity";

    boolean isNew = false;
    EditText question;
    EditText answer;
    EditText labels;
    Button save;
    Button delete;
    UserLocalDataStore userLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        question = findViewById(R.id.questionTextMultiLine);
        answer = findViewById(R.id.answerTextMultiLine);
        labels = findViewById(R.id.labelsText);
        save = findViewById(R.id.saveButton);
        delete = findViewById(R.id.deleteButton);
        delete.setEnabled(false);
        userLocal = new UserLocalDataStore(question.getContext());

        String id = this.getIntent().getStringExtra(CardField.ID.getValue());
        if(id != null){
            new CardController(this).load(id);
        } else {
            isNew = true;
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isDataValid()) return;

                    new CardController(CardDataActivity.this).insert(
                            userLocal.getID(),
                            userLocal.getUsername(),
                            question.getText().toString(),
                            answer.getText().toString(),
                            labels.getText().toString()
                    );
                }
            });
        }
    }

    private boolean isDataValid() {
        List<String> result = new ArrayList<>();

        if(question.getText().toString().isEmpty() ||
                answer.getText().toString().isEmpty() ||
                labels.getText().toString().isEmpty()
        ){
            result.add("All fields are required");
        }

        if(!Labelling.isValid(labels.getText().toString())){
            result.add("Labels must have only letters, numbers, dashes or underscores");
        }

        if(result.size() > 0){
            showToast(toLines(result));
            return false;
        }
        return true;
    }

    private String toLines(List<String> messages){
        String result = "";
        int i = 0;
        for(String message : messages){
            result += message;
            if(i < messages.size() - 1 ) {
                result += "\n";
            }
        }
        return result;
    }

    @Override
    public void onLoad(Card card) {
        question.setText(card.getQuestion());
        answer.setText(card.getAnswer());
        labels.setText(card.getLabelling().toString());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CardController(CardDataActivity.this).update(
                        card.getId(), card.getAuthorID(), card.getAuthorUsername(),
                        question.getText().toString(),
                        answer.getText().toString(),
                        labels.getText().toString()
                );
            }
        });
        delete.setEnabled(true);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteConfirmDialog(card.getId());
            }
        });
    }

    public void openDeleteConfirmDialog(String cardID){
        DialogFragment dialog = new DeleteCardDialogFragment(cardID);
        dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }

    @Override
    public void onLoad(Subscription subscription) {

    }

    @Override
    public void onLoadAllSubscriptions(List<Subscription> subscriptions) {

    }

    @Override
    public void onDelete(String id) {
        this.showToast(getString(R.string.card_deleted));
        new SubscriptionRepository(this).deleteSubscriptionsWithCardID(id);
        finish();
    }

    @Override
    public void onError(DataStoreError error) {
        Map<DataStoreError, String> errorMessages = new HashMap<>();
        errorMessages.put(DataStoreError.ON_INSERT, getString(R.string.error_inserting_card));
        errorMessages.put(DataStoreError.ON_LOAD, getString(R.string.error_loading_card));
        errorMessages.put(DataStoreError.ON_LOAD_ALL, getString(R.string.error_loading_all_cards));
        errorMessages.put(DataStoreError.ON_SEARCH, getString(R.string.error_searching_cards));
        errorMessages.put(DataStoreError.ON_UPDATE, getString(R.string.error_updateing_card));
        errorMessages.put(DataStoreError.ON_DELETE, getString(R.string.error_deleting_card));
        this.showToast(errorMessages.get(error));
    }

    @Override
    public void onSave(Subscription subscription) {

    }

    @Override
    public void onSave(Card card) {
        this.showToast(getString(R.string.card_saved));
        if (isNew){
            new SubscriptionRepository((SubscriptionDataStoreListener) this)
                    .subscribeUserToCard(userLocal.getID(), card.getId());
        }
        finish();
    }

    @Override
    public void onLoadAllCards(List<Card> cards) {

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.d(TAG, "showToast: " + message);
    }

    @Override
    public void onDeleteDialogConfirmClick(DialogFragment dialog, String cardID) {
        new CardController(CardDataActivity.this).delete(cardID);
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {

    }

    @Override
    protected String getNavBarTitle() {
        if(this.getIntent().getStringExtra(CardField.ID.getValue()) == null) {
            return getString(R.string.create_card_screen_title);
        }
        return getString(R.string.edit_card_screen_title);
    }
}