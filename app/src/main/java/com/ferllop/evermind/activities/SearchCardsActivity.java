package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.controllers.CardController;

public class SearchCardsActivity extends AppCompatActivity implements DatastoreListener<Card> {

    final String TAG = "SearchCardsActivity";
    CardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);
        CardController cardController = new CardController(this);
        RecyclerView recycler = findViewById(R.id.card_frame_recycler);
        adapter = new CardsAdapter();
        recycler.setAdapter(adapter);
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                String searchText = ((EditText) findViewById(R.id.searchBar_textInput)).getText().toString();
                if(searchText.equals("all")){
                    cardController.getAll();
                } else {
                    try{
                        cardController.getFromSearch(searchText);
                    } catch (IllegalArgumentException ex) {
                        Toast.makeText(SearchCardsActivity.this, R.string.error_empty_query_search, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onSave() { }

    @Override
    public void onLoad(Card card) {
        adapter.addCard(card);
    }

    @Override
    public void onDelete(){ }

    @Override
    public void onError(DataStoreError error) {
        Toast.makeText(this, R.string.error_searching_card, Toast.LENGTH_LONG).show();
    }
}