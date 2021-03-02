package com.ferllop.evermind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.Arrays;
import java.util.List;

public class CardDataActivity extends AppCompatActivity {

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        Card card = new Card();
        this.loadCard("SaCSnWbB98dB3T1JM1Gl");

        this.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                List<String> labels = Card.parseLabels(((EditText) findViewById(R.id.labelsText)).getText().toString());

                //new dbController(CardDataActivity.this).save(new Card(question, answer, labels));
            }
        });
    }

    private void loadCard(String id){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("cards").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                card = documentSnapshot.toObject(Card.class);
                ((EditText) findViewById(R.id.questionTextMultiLine)).setText(card.getQuestion());
                ((EditText) findViewById(R.id.answerTextMultiLine)).setText(card.getAnswer());
                ((EditText) findViewById(R.id.labelsText)).setText(Arrays.toString(card.getLabels().toArray()));
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