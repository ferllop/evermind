package com.ferllop.evermind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;

import java.util.ArrayList;
import java.util.List;

public class MySubscriptions extends MainNavigationActivity implements SearchResultsFragment.OnSearchHaveResults {

    TextView countMessage;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscriptions);
        countMessage = findViewById(R.id.my_subscriptions_count_txt);
        searchButton = findViewById(R.id.my_subscriptions_search_button);
        countMessage.setVisibility(View.INVISIBLE);
        searchButton.setVisibility(View.GONE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySubscriptions.this, SearchCardsActivity.class));
            }
        });
        load();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        load();
    }

    private void load() {
        List<String> cardsToLoad = new ArrayList<>();
        for(Subscription sub : SubscriptionsGlobal.getInstance().getSubscriptions()){
            cardsToLoad.add(sub.getCardID());
        }

        SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance((String[]) cardsToLoad.toArray(new String[0]));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.my_subscriptions_results_container, searchResultsFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.my_subscriptions_screen_title);
    }

    @Override
    public void cardsCount(int count) {
        setCountIntoTextView(count);
    }

    @Override
    public void onSubscriptionUpdate(int count) {
        setCountIntoTextView(count);
    }

    private void setCountIntoTextView(int count){
        if (count == 0){
            countMessage.setText(R.string.no_subscriptions_found);
            searchButton.setVisibility(View.VISIBLE);
        } else {
            searchButton.setVisibility(View.GONE);
            String subscriptionsMessage = getString(R.string.subscribed_count_message);
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