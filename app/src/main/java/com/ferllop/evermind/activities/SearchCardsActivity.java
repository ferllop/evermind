package com.ferllop.evermind.activities;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchInfoDialog;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.controllers.CardController;

import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.List;


public class SearchCardsActivity extends MainNavigationActivity implements CardDataStoreListener, SubscriptionDataStoreListener {

    final String TAG = "SearchCardsActivity";
    CardsAdapter adapter;
    CardController cardController;
    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);

        searchField = findViewById(R.id.searchBar_textInput);

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

        findViewById(R.id.search_info_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new SearchInfoDialog();
                dialog.show(getSupportFragmentManager(), "SearchInfoDialog");
            }
        });
    }

    private void executeSearch(){
        adapter.clear();
        String searchText = searchField.getText().toString();
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
        hideKeyboard(searchField);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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