package com.ferllop.evermind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Search;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchCardsActivity extends AppCompatActivity {

    final String TAG = "SearchCardsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);
        RecyclerView recycler = findViewById(R.id.card_frame_recycler);
        CardsAdapter adapter = new CardsAdapter();
        recycler.setAdapter(adapter);

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = ((EditText) findViewById(R.id.searchBar_textInput)).getText().toString();
                    adapter.clear();
                    if(searchText.equals("all")){
                        SearchCardsActivity.this.getAllCards(adapter);
                    } else {
                        try{
                            SearchCardsActivity.this.getCards(searchText, adapter);
                        } catch (IllegalArgumentException ex) {
                            Toast.makeText(getApplicationContext(), R.string.empty_query_search_error, Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });
    }

    private void getCards(String query, CardsAdapter adapter) {
        Search search = new Search(query);

        Query dbQuery = FirebaseFirestore.getInstance().collection("cards");
        if(search.hasLabels()){
            for(String label: search.getLabels()){
                dbQuery = dbQuery.whereEqualTo("labels."+label, true);
            }
        }
        if(search.hasAuthor()){
            dbQuery = dbQuery.whereEqualTo("author", search.getAuthor());
        }

        dbQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        Card card = document.toObject(Card.class);
                        adapter.addCard(id, card);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void getAllCards(CardsAdapter adapter){
        FirebaseFirestore.getInstance().collection("cards").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            Card card = document.toObject(Card.class);
                            adapter.addCard(id, card);
                        }
                    } else {
                        Log.d(SearchCardsActivity.this.TAG, "failed getting all cards");
                    }
                });
    }

}