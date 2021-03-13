package com.ferllop.evermind.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.DataStoreError;
import com.ferllop.evermind.models.SubscribedCard;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.CardFirestoreRepository;
import com.ferllop.evermind.repositories.DatastoreListener;
import com.ferllop.evermind.repositories.SubscriptionFirestoreRepository;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements DatastoreListener<Card> {
    final String TAG = "MYAPP-ReviewActivity";

    List<SubscribedCard> cardsToReview;
    List<Subscription> cardsToLoad;
    TextView question;
    TextView answer;
    Button resolve;
    Button right;
    Button wrong;
    int loadedCards;
    int cardsToReviewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        question = findViewById(R.id.review_question_textview);
        answer = findViewById(R.id.review_answer_textview);
        resolve = findViewById(R.id.review_resolve_button);
        right = findViewById(R.id.review_right_button);
        wrong = findViewById(R.id.review_wrong_button);

        question.setVisibility(View.INVISIBLE);
        resolve.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);

        CardFirestoreRepository cardRepo = new CardFirestoreRepository(this);
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

    private void reviewSubscriptions() {
        if (cardsToReviewCount < 1){
            Log.d(TAG, "reviewSubscriptions: back!!!");
            finish();
            return;
        }

        answer.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);

        SubscribedCard subCard = cardsToReview.get(--cardsToReviewCount);
        this.printCard(subCard.getCard());
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubscriptionFirestoreRepository(null).upgradeLevel(subCard.getSubscription());
                ReviewActivity.this.reviewSubscriptions();
            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubscriptionFirestoreRepository(null).downgradeLevel(subCard.getSubscription());
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
                answer.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                wrong.setVisibility(View.VISIBLE);
            }
        });
    }

    private void printCard(Card card){
        String result = "empty";
        if(card != null){
            result = card.getQuestion();
        }
        Log.d("MYAPP", "printCard: " + result);
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onError(DataStoreError error) {

    }

    @Override
    public void onSave() {

    }
}