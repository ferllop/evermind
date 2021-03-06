package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.controllers.CardController;
import com.ferllop.evermind.repositories.fields.CardField;

import java.util.HashMap;
import java.util.Map;


public class CardDataActivity extends AppCompatActivity implements DatastoreListener<Card> {
    final private String TAG = "CardDataActivityClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        String id = this.getIntent().getStringExtra(CardField.ID.getValue());
        if(id != null){
            new CardController(this).load(id);
        } else {
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                    String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                    String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                    new CardController(CardDataActivity.this).insert(question, answer, labels);
                }
            });
        }
    }

    @Override
    public void onSave() {
        this.showToast(getString(R.string.card_saved));
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
                cardController.update(card.getId(), card.getAuthor(), question, answer, labels);
            }
        });
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardController.delete(card.getId());
            }
        });
    }

    @Override
    public void onDelete(){
        this.showToast(getString(R.string.card_deleted));
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

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}