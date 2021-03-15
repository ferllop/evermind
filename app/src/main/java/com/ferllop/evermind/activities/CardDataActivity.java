package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.AndroidApplication;
import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.DeleteCardDialogFragment;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.SubscriptionRepository;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.controllers.CardController;
import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDataActivity extends AppCompatActivity implements
        CardDataStoreListener, SubscriptionDataStoreListener, DeleteCardDialogFragment.DeleteDialogListener {
    final private String TAG = "CardDataActivityClass";

    boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        String id = this.getIntent().getStringExtra(CardField.ID.getValue());
        if(id != null){
            new CardController(this).load(id);
        } else {
            isNew = true;
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                    String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                    String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                    new CardController(CardDataActivity.this).insert(AndroidApplication.getUserID(CardDataActivity.this), AndroidApplication.getUser(CardDataActivity.this), question, answer, labels);
                }
            });
        }
    }

    @Override
    public void onLoad(Card card) {
        CardController cardController = new CardController(this);
        ((EditText) findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
        ((EditText) findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
        ((EditText) findViewById(R.id.labelsText)).setText(card.getLabelling().toString());
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                cardController.update(card.getId(), card.getAuthorID(), card.getAuthorUsername(), question, answer, labels);
            }
        });
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
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
    public void onLoadAll(List<Subscription> subscriptions) {

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
                    .subscribeUserToCard(AndroidApplication.getUserID(this), card.getId());
            finish();
        }
    }

    @Override
    public void onNotFound() {

    }

    @Override
    public void onLoadAllCards(List<Card> cards) {

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteDialogConfirmClick(DialogFragment dialog, String cardID) {
        new CardController(CardDataActivity.this).delete(cardID);
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {

    }
}