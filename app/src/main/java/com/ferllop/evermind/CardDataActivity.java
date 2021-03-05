package com.ferllop.evermind;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    Card card = createCardFrom(CardDataActivity.this);
                    cardController.insert(card);
                }
            });
        }
    }

    private Card createCardFrom(Activity activity) {
        return this.modifyCardFrom(null, activity);
    }

    private Card modifyCardFrom(Card card, Activity activity){
        String author;
        if (card == null) {
            author = "anonymous";
        } else {
            author = card.getAuthor();

        }
        String question = ((EditText) activity.findViewById(R.id.questionTextMultiLine)).getText().toString();
        String answer = ((EditText) activity.findViewById(R.id.answerTextMultiLine)).getText().toString();
        String labels = ((EditText) activity.findViewById(R.id.labelsText)).getText().toString();
        return new Card(author, question, answer, labels);
    }

    @Override
    public void onSave() {
        this.showToast("Card saved successfully");
    }

    @Override
    public void onLoad(Card card) {
        ((EditText) findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
        ((EditText) findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
        ((EditText) findViewById(R.id.labelsText)).setText(card.stringifyLabels());
        ((Button)   findViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card modifiedCard = modifyCardFrom(card, CardDataActivity.this);
                cardController.update(card.getId(), modifiedCard);
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