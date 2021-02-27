package com.ferllop.evermind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ferllop.evermind.models.Card;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.RegEx;

public class CardDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionTextMultiLine)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextMultiLine)).getText().toString();
                List<String> labels = Card.parseLabels(((EditText) findViewById(R.id.labelsText)).getText().toString());

                Map<String, Object> card = new HashMap<>();
                card.put("question", question);
                card.put("answer", answer);
                card.put("labels", labels);

                db.collection("cards").add(card);
            }
        });
    }
}