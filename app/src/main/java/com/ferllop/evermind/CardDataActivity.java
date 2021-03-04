package com.ferllop.evermind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.models.Card;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CardDataActivity extends AppCompatActivity {
    final private String TAG = "CardDataActivityClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        Log.d(TAG, "onCreate");

        String id = this.getIntent().getStringExtra("id");
        if(id != null){
            Log.d(TAG, "the id is " + id);
            this.dbLoadCard(id);
        } else {
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card card = createCardFrom(CardDataActivity.this);
                    dbInsertCard(card);
                }
            });
        }
    }

    class IdentifiedCard {
        String id;
        Card card;

        public IdentifiedCard(String id, Card card){
            this.id = id;
            this.card = card;
        }

        public Card getCard(){
            return card;
        }

        public String getId(){
            return id;
        }
    }

    private void onLoad(IdentifiedCard card){
        setCardOnActivity(card, this);
    }

    private void dbLoadCard(String id){
        FirebaseFirestore.getInstance().collection("cards").document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        onLoad(new IdentifiedCard(id, documentSnapshot.toObject(Card.class)));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CardDataActivity.this, "fallaaaaa", Toast.LENGTH_LONG).show();
                        Log.d("el tag", "fallaaaa");
                    }
                });
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
                dbUpdateCard(identifiedCard.getId(), modifiedCard);
            }
        });
        CardDataActivity.this.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDeleteCard(identifiedCard.getId());
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

    private void dbInsertCard(Card card) {
        FirebaseFirestore.getInstance().collection("cards")
                .add(card)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void dbUpdateCard(String id, Card card) {
        FirebaseFirestore.getInstance().collection("cards").document(id)
                .set(card)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void dbDeleteCard(String id) {
        FirebaseFirestore.getInstance().collection("cards").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}