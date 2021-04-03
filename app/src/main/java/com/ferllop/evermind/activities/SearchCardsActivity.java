package com.ferllop.evermind.activities;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchInfoDialog;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;


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

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.search_screen_title);
    }

    private void executeSearch(){
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(searchField.getText().toString());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.search_cards_container, searchResultsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        executeSearch();
    }
}