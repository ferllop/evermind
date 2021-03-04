package com.ferllop.evermind;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.db.DbController;
import com.ferllop.evermind.db.DbUser;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.IdentifiedCard;

public class CardDataActivity extends AppCompatActivity implements DbUser {
    final private String TAG = "CardDataActivityClass";
    private DbController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        Log.d(TAG, "onCreate");
        db = new DbController(this);

        String id = this.getIntent().getStringExtra("id");
        if(id != null){
            Log.d(TAG, "the id is " + id);
            db.loadCard(id);
        } else {
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card card = createCardFrom(CardDataActivity.this);
                    db.insertCard(card);
                }
            });
        }
    }

    private void setCardOnActivity(IdentifiedCard identifiedCard, Activity activity) {
        Card card = identifiedCard.getCard();
        ((EditText) activity.findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
        ((EditText) activity.findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
        ((EditText) activity.findViewById(R.id.labelsText)).setText(card.stringifyLabels());
        ((Button)   activity.findViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card modifiedCard = modifyCardFrom(card, CardDataActivity.this);
                db.updateCard(identifiedCard.getId(), modifiedCard);
            }
        });
        CardDataActivity.this.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCard(identifiedCard.getId());
            }
        });
    }

    private Card createCardFrom(Activity activity) {
        return this.modifyCardFrom(new Card(), activity);
    }

    private Card modifyCardFrom(Card card, Activity activity){
        String author = card.getAuthor();
        String question = ((EditText) activity.findViewById(R.id.questionTextMultiLine)).getText().toString();
        String answer = ((EditText) activity.findViewById(R.id.answerTextMultiLine)).getText().toString();
        String labels = ((EditText) activity.findViewById(R.id.labelsText)).getText().toString();
        return new Card(author, question, answer, labels);
    }

    @Override
    public void onLoad(IdentifiedCard card){
        setCardOnActivity(card, this);
    }

    @Override
    public void onSave() {
        this.showToast("Card saved successfully");
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