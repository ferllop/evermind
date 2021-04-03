package com.ferllop.evermind.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

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

    @Override
    protected void onRestart() {
        super.onRestart();
        load();
    }

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.my_cards_screen_title);
    }

    private void load(){
        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(
                "@" + new UserLocalDataStore(this).getUsername());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.my_cards_container, searchResultsFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}