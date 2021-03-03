package com.ferllop.evermind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        Log.d(TAG, "onCreate");
        Card card = new Card();

        String id = this.getIntent().getStringExtra("id");
        if(id != null){
            Log.d(TAG, "the id is " + id);
            this.loadCard(id);
        } else {
            this.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                    String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                    String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();
                    CardDataActivity.this.card = new Card();
                    CardDataActivity.this.card.setQuestion(question);
                    CardDataActivity.this.card.setAnswer(answer);
                    CardDataActivity.this.card.setLabels(CardDataActivity.this.card.mapLabels(labels));
                    CardDataActivity.this.card.setAuthor("newAuthor");

                    FirebaseFirestore.getInstance().collection("cards")
                            .add(CardDataActivity.this.card)
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
            });
        }




    }

    private void loadCard(String id){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("cards").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                card = documentSnapshot.toObject(Card.class);

                ((EditText) findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
                ((EditText) findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
                ((EditText) findViewById(R.id.labelsText)).setText(card.stringifyLabels());




                CardDataActivity.this.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                        String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                        String labels = ((EditText) findViewById(R.id.labelsText)).getText().toString();

                        card.setQuestion(question);
                        card.setAnswer(answer);
                        card.setLabels(CardDataActivity.this.card.mapLabels(labels));

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
                });

                CardDataActivity.this.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


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
                });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CardDataActivity.this, "fallaaaaa", Toast.LENGTH_LONG).show();
                Log.d("el tag", "fallaaaa");
            }
        });
    }


    //public void remove(Storable storable){
        //new dbController(new Firestore()).remove(storable);
    //}
}