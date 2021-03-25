package com.ferllop.evermind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.models.SubscribedCard;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.SubscriptionRepository;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements CardDataStoreListener {
    final String TAG = "MYAPP-ReviewActivity";

    List<SubscribedCard> cardsToReview;
    List<Subscription> cardsToLoad;
    TextView question;
    TextView answer;
    ImageButton resolve;
    ImageButton right;
    ImageButton wrong;
    ProgressBar progress;
    int loadedCards;
    int cardsToReviewCount;
    int totalRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        question = findViewById(R.id.review_question_textview);
        answer = findViewById(R.id.review_answer_textview);
        resolve = findViewById(R.id.review_resolve_button);
        right = findViewById(R.id.review_right_button);
        wrong = findViewById(R.id.review_wrong_button);
        progress = findViewById(R.id.review_progressBar);
        totalRight = 0;

        question.setVisibility(View.INVISIBLE);
        resolve.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);

        CardRepository cardRepo = new CardRepository(this);
        cardsToReview = new ArrayList<>();
        loadedCards = 0;
        cardsToLoad = SubscriptionsGlobal.getInstance().getTenOrRemainingForToday();
        for(Subscription sub : cardsToLoad){
            cardRepo.load(sub.getCardID());
            cardsToReview.add(new SubscribedCard(sub.getCardID(), sub));
        }
    }

    @Override
    public void onLoad(Card card) {
        for (SubscribedCard subCard: cardsToReview){
            if (subCard.getCardID().equals(card.getId())){
                subCard.setCard(card);
            }
        }
        loadedCards++;

        if (loadedCards == this.cardsToLoad.size()){
            cardsToReviewCount = cardsToReview.size();
            reviewSubscriptions();
        }
    }


    @Override
    public void onDelete(String id) {

    }

    private void reviewSubscriptions() {
        if (cardsToReviewCount < 1){
            startActivity(
                    new Intent(getApplicationContext(), ReviewResultActivity.class)
                        .putExtra("total", cardsToReview.size())
                        .putExtra("right", totalRight)
            );
            return;
        }

        answer.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);

        SubscribedCard subCard = cardsToReview.get(--cardsToReviewCount);
        progress.setProgress((int) SubscriptionsGlobal.getInstance().getProgress(cardsToReviewCount, cardsToReview.size()));

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalRight++;
                new SubscriptionRepository(null).upgradeLevel(subCard.getSubscription());
                ReviewActivity.this.reviewSubscriptions();
            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubscriptionRepository(null).downgradeLevel(subCard.getSubscription());
                ReviewActivity.this.reviewSubscriptions();
            }
        });
        question.setText(subCard.getCard().getQuestion());
        question.setVisibility(View.VISIBLE);
        resolve.setVisibility(View.VISIBLE);
        answer.setText(subCard.getCard().getAnswer());
        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve.setVisibility(View.INVISIBLE);
                answer.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                wrong.setVisibility(View.VISIBLE);
            }
        });
    }



    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave(Card item) {

    }

    @Override
    public void onLoadAllCards(List<Card> cards) {

    }

}