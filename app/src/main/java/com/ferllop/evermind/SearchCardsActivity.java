package com.ferllop.evermind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.db.DbController;
import com.ferllop.evermind.db.DbUser;
import com.ferllop.evermind.models.IdentifiedCard;

public class SearchCardsActivity extends AppCompatActivity implements DbUser {

    final String TAG = "SearchCardsActivity";
    CardsAdapter adapter;
    DbController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);
        db = new DbController(this);
        RecyclerView recycler = findViewById(R.id.card_frame_recycler);
        adapter = new CardsAdapter();
        recycler.setAdapter(adapter);
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = ((EditText) findViewById(R.id.searchBar_textInput)).getText().toString();
                adapter.clear();
                if(searchText.equals("all")){
                    db.getAllCards(adapter);
                } else {
                    try{
                        db.getCards(searchText, adapter);
                    } catch (IllegalArgumentException ex) {
                        onError(getString(R.string.empty_query_search_error));
                    }
                }
            }
        });
    }

    @Override
    public void onLoad(IdentifiedCard card){
        adapter.addCard(card);
    }

    @Override
    public void onSave() {

    }

    @Override
    public void onDelete(){

    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}