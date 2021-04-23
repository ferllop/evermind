package com.ferllop.evermind.activities;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchInfoDialog;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;


public class SearchCardsActivity extends MainNavigationActivity
        implements SearchResultsFragment.OnSearchHaveResults {

    final String TAG = "MYAPP-SearchCardsActi";

    EditText searchField;
    TextView cardsCount;
    Button subscribeAll;
    SearchResultsFragment searchResultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cards);

        searchField = findViewById(R.id.searchBar_textInput);
        cardsCount = findViewById(R.id.search_cards_found_txt);
        cardsCount.setVisibility(View.INVISIBLE);
        subscribeAll = findViewById(R.id.search_subscribe_all_button);
        subscribeAll.setVisibility(View.INVISIBLE);

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

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.search_screen_title);
    }

    private void executeSearch(){
        searchResultsFragment = SearchResultsFragment.newInstance(searchField.getText().toString());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.search_cards_container, searchResultsFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        executeSearch();
    }

    @Override
    public void cardsCount(int count) {
        setCountIntoTextView(count);
    }

    @Override
    public void onSubscriptionUpdate(int count) {

    }

    private void setCountIntoTextView(int count){
        if (count == 0){
            cardsCount.setText(R.string.no_cards_found);
            subscribeAll.setVisibility(View.INVISIBLE);
        } else {
            String cardsFoundMessage = getString(R.string.cards_found_quantity);
            cardsFoundMessage = cardsFoundMessage.replaceAll("#qty", String.valueOf(count));
            if(count == 1){
                cardsFoundMessage = cardsFoundMessage.replaceAll("#s", "");
            } else {
                cardsFoundMessage = cardsFoundMessage.replaceAll("#s", "s");
            }
            cardsCount.setText(cardsFoundMessage);
            subscribeAll.setVisibility(View.VISIBLE);

            findViewById(R.id.search_subscribe_all_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchResultsFragment.subscribeToAll(SearchCardsActivity.this);
                }
            });
        }
        cardsCount.setVisibility(View.VISIBLE);
    }
}