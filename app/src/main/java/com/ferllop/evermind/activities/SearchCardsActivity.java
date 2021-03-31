package com.ferllop.evermind.activities;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchInfoDialog;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.controllers.CardController;

import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.List;


public class SearchCardsActivity extends MainNavigationActivity {

    final String TAG = "MYAPP-SearchCardsActi";

    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);

        searchField = findViewById(R.id.searchBar_textInput);

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
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(searchField.getText().toString());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, searchResultsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        executeSearch();
    }
}