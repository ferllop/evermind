package com.ferllop.evermind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ferllop.evermind.R;
import com.ferllop.evermind.repositories.datastores.UserLocalDataStore;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.SubscriptionRepository;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.List;

public class HomeActivity extends MainNavigationActivity
        implements SubscriptionDataStoreListener {
    final String TAG = "MYAPP-Home";
    Button startButton;
    Button searchButton;
    Button createButton;
    TextView cardsCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getAllSubsFromUser();

        searchButton = findViewById(R.id.search_cards_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchCardsActivity.class));
            }
        });

        createButton = findViewById(R.id.create_card_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CardDataActivity.class));
            }
        });
    }

    @Override
    protected String getNavBarTitle() {
        return getString(R.string.home_screen_title);
    }

    private void getAllSubsFromUser() {
        ((Button) findViewById(R.id.review_cards_button)).setEnabled(false);
        findViewById(R.id.review_cards_textView).setVisibility(View.INVISIBLE);
        new SubscriptionRepository(this).getAllFromUser(new UserLocalDataStore(this).getID());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getAllSubsFromUser();
    }

    private void setCount(int count) {
        cardsCountText = findViewById(R.id.review_cards_textView);
        cardsCountText.setVisibility(View.INVISIBLE);
        String cardsToReview = getString(R.string.cards_to_review);
        cardsToReview = cardsToReview.replace("#qty", String.valueOf(count));
        if(count == 1){
            cardsToReview = cardsToReview.replace("#s", "");
        } else {
            cardsToReview = cardsToReview.replace("#s", "s");
        }
        cardsCountText.setText(cardsToReview);
        cardsCountText.setVisibility(View.VISIBLE);

        startButton = (Button) findViewById(R.id.review_cards_button);

        if(count > 0){
            startButton.setEnabled(true);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this, ReviewActivity.class));
                }
            });
        }
    }

    @Override
    public void onLoadAllSubscriptions(List<Subscription> subs) {
        SubscriptionsGlobal.getInstance().setAllSubscriptions(subs);
        int dayStartTime = Integer.parseInt(new UserLocalDataStore(this).getDayStartTime());
        List<Subscription> subsForToday = SubscriptionsGlobal.getInstance().getSubscriptionsForToday(dayStartTime);
        setCount(subsForToday.size());
    }

    @Override
    public void onLoad(Subscription subscription) {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave(Subscription subscription) {

    }

    @Override
    public void onDelete(String subscriptionID) {

    }

}