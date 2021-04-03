package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.fragments.SearchResultsFragment;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;

import java.util.ArrayList;
import java.util.List;

public class MySubscriptions extends MainNavigationActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscriptions);

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
        fragmentTransaction.replace(R.id.my_subscriptions_container, searchResultsFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.my_subscriptions_screen_title);
    }
}