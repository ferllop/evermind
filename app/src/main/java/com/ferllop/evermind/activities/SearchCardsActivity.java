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
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.controllers.CardController;

import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.CrudDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.List;


public class SearchCardsActivity extends AppCompatActivity implements CardDataStoreListener, SubscriptionDataStoreListener {

    final String TAG = "SearchCardsActivity";
    CardsAdapter adapter;
    CardController cardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);
        cardController = new CardController(this);
        RecyclerView recycler = findViewById(R.id.card_frame_recycler);
        adapter = new CardsAdapter();
        recycler.setAdapter(adapter);
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeSearch();
            }
        });
    }

    private void executeSearch(){
        adapter.clear();

        String searchText = ((EditText) findViewById(R.id.searchBar_textInput)).getText().toString();
        if (searchText.equals("all")) {
            cardController.getAll();
        } else {
            try {
                cardController.getFromSearch(searchText);
            } catch (IllegalArgumentException ex) {
                Toast.makeText(SearchCardsActivity.this, R.string.error_empty_query_search, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        executeSearch();
    }

    @Override
    public void onSave(Subscription subscription) {
        adapter.updateCard(subscription.getCardID());
        SubscriptionsGlobal.getInstance().addSubscription(subscription);
        Toast.makeText(this, R.string.subscribed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadAll(List<Subscription> subscriptions) {

    }

    public void onLoad(Card card) {
        adapter.addCard(card);
    }

    @Override
    public void onDelete(String subscriptionID) {
        adapter.updateCard(SubscriptionsGlobal.getInstance().getCardIdFrom(subscriptionID));
        SubscriptionsGlobal.getInstance().deleteSubscription(subscriptionID);
        Toast.makeText(this, R.string.unsubscribed_from_card, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoad(Subscription subscription) {

    }

    @Override
    public void onNotFound() {

    }

    @Override
    public void onLoadAllCards(List<Card> items) {

    }


    @Override
    public void onError(DataStoreError error) {
        Toast.makeText(this, R.string.error_searching_card, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSave(Card item) {

    }
}