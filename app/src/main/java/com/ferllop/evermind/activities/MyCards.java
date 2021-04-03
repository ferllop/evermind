package com.ferllop.evermind.activities;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;

public class MyCards extends MainNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        load();
    }

    private void load(){
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(
                "@" + new UserLocalDataStore(this).getUsername());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.my_cards_container, searchResultsFragment);
        fragmentTransaction.commit();
    }
}