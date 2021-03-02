package com.ferllop.evermind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ferllop.evermind.models.Card;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchCardsActivity extends AppCompatActivity {

    final String TAG = "SearchCardsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);

        RecyclerView recycler = findViewById(R.id.card_frame_recycler);
        CardsAdapter adapter = new CardsAdapter(new ArrayList<Card>());
        recycler.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("cards").get()
               .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       Log.d(SearchCardsActivity.this.TAG, "successfull getting all cards");
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           adapter.addCard(document.toObject(Card.class));
                       }
                   } else {
                       Log.d(SearchCardsActivity.this.TAG, "failed getting all cards");
                   }
               });
    }

}