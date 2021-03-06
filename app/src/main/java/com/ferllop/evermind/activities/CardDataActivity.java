package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.controllers.CardController;


public class CardDataActivity extends AppCompatActivity implements DatastoreListener<Card> {
    final private String TAG = "CardDataActivityClass";

    private CardController cardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        Log.d(TAG, "onCreate");
        cardController = new CardController(this);

        String id = this.getIntent().getStringExtra("id");
        if(id != null){
            Log.d(TAG, "the id is " + id);
            cardController.load(id);
        } else {
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                    String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                    String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                    cardController.insert(question, answer, labels);
                }
            });
        }
    }

    @Override
    public void onSave() {
        this.showToast("Card saved successfully");
    }

    @Override
    public void onLoad(Card card) {
        ((EditText) findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
        ((EditText) findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
        ((EditText) findViewById(R.id.labelsText)).setText(card.getCommaSeparatedLabels());
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                cardController.update(card.getId(), card.getAuthor(), question, answer, labels);
            }
        });
        CardDataActivity.this.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardController.delete(card.getId());
            }
        });
    }

    @Override
    public void onDelete(){
        this.showToast("Card deleted");
    }

    @Override
    public void onError(String errorMessage) {
        this.showToast(errorMessage);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}