package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;

public class MyCards extends MainNavigationActivity implements SearchResultsFragment.OnSearchHaveResults {

    TextView countMessage;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        countMessage = findViewById(R.id.my_cards_count_txt);
        createButton = findViewById(R.id.my_cards_create_button);
        countMessage.setVisibility(View.INVISIBLE);
        createButton.setVisibility(View.GONE);

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

    @Override
    public void cardsCount(int count) {
        setCountIntoTextView(count);
    }

    @Override
    public void onSubscriptionUpdate(int count) {

    }

    private void setCountIntoTextView(int count){
        if (count == 0){
            countMessage.setText(R.string.no_created_cards_found);
            createButton.setVisibility(View.VISIBLE);
        } else {
            createButton.setVisibility(View.GONE);
            String subscriptionsMessage = getString(R.string.created_cards_count_message);
            subscriptionsMessage = subscriptionsMessage.replaceAll("#qty", String.valueOf(count));
            if(count == 1){
                subscriptionsMessage = subscriptionsMessage.replaceAll("#s", "");
            } else {
                subscriptionsMessage = subscriptionsMessage.replaceAll("#s", "s");
            }
            countMessage.setText(subscriptionsMessage);
        }
        countMessage.setVisibility(View.VISIBLE);
    }
}