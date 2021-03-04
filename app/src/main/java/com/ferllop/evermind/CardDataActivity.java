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
import com.ferllop.evermind.db.FirestoreService;
import com.ferllop.evermind.db.ModelDao;
import com.ferllop.evermind.models.Card;

public class CardDataActivity extends AppCompatActivity implements DbUser {
    final private String TAG = "CardDataActivityClass";
    private DbController dbCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        Log.d(TAG, "onCreate");
        dbCards = new DbController<Card>("cards", Card.class,this);

        String id = this.getIntent().getStringExtra("id");
        if(id != null){
            Log.d(TAG, "the id is " + id);
            dbCards.getCard(id);
        } else {
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card card = createCardFrom(CardDataActivity.this);
                    dbCards.insertCard(card);
                }
            });
        }
    }

    private void setCardOnActivity(ModelDao cardDao, Activity activity) {
        Card card = (Card) cardDao.getModel();
        ((EditText) activity.findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
        ((EditText) activity.findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
        ((EditText) activity.findViewById(R.id.labelsText)).setText(card.stringifyLabels());
        ((Button)   activity.findViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card modifiedCard = modifyCardFrom(card, CardDataActivity.this);
                dbCards.updateCard(cardDao.getId(), modifiedCard);
            }
        });
        CardDataActivity.this.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCards.deleteCard(cardDao.getId());
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
    public void onLoad(ModelDao card){
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