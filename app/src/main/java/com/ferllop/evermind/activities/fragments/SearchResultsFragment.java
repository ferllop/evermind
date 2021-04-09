package com.ferllop.evermind.activities.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ferllop.evermind.R;
import com.ferllop.evermind.activities.CardsAdapter;
import com.ferllop.evermind.controllers.CardController;
import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Subscription;
import com.ferllop.evermind.repositories.CardRepository;
import com.ferllop.evermind.repositories.SubscriptionsGlobal;
import com.ferllop.evermind.repositories.fields.DataStoreError;
import com.ferllop.evermind.repositories.listeners.CardDataStoreListener;
import com.ferllop.evermind.repositories.listeners.SubscriptionDataStoreListener;

import java.util.List;

public class SearchResultsFragment extends Fragment implements CardDataStoreListener, SubscriptionDataStoreListener {

    private static final String SEARCH_QUERY = "searchQuery";
    private static final String CARDS_ID = "cardsID";
    private static final String TAG = "MYAPP-SearchFrag";

    private OnSearchHaveResults listener;
    private String searchQuery;
    private String[] cardsID;
    private CardsAdapter adapter;
    private CardController cardController;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    public static SearchResultsFragment newInstance(String searchQuery) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY, searchQuery);
        args.putStringArray(CARDS_ID, null);
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchResultsFragment newInstance(String[] cardsID) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY, null);
        args.putStringArray(CARDS_ID, cardsID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getString(SEARCH_QUERY) != null){
                searchQuery = getArguments().getString(SEARCH_QUERY);
            } else {
                cardsID =  getArguments().getStringArray(CARDS_ID);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardController = new CardController(this);
        RecyclerView recycler = getView().findViewById(R.id.card_frame_recycler);
        adapter = new CardsAdapter(this);
        recycler.setAdapter(adapter);
        execute();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnSearchHaveResults) context;
    }

    private void execute(){
        if (searchQuery != null){
            executeSearch(searchQuery);
        } else {
            loadCards(cardsID);
        }
        listener.cardsCount(adapter.getItemCount());
    }

    private void executeSearch(String searchText){
        adapter.clear();

        if (searchText.equals("all")) {
            cardController.getAll();
        } else if (searchText.equals("ownSubscriptions")) {
            SubscriptionsGlobal.getInstance().getSubscriptions();
        } else {
            try {
                cardController.getFromSearch(searchText);
            } catch (IllegalArgumentException ex) {
                Toast.makeText(getActivity(), R.string.error_empty_query_search, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadCards(String[] cardsID){
        for(String cardID : cardsID){
            new CardRepository(this).load(cardID);
        }
    }

    public void subscribeToAll(Context context){
        adapter.subscribeToAll(context);
        Toast.makeText(getActivity(), R.string.subscribed_to_all, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSave(Subscription subscription) {
        adapter.updateCard(subscription.getCardID());
        SubscriptionsGlobal.getInstance().addSubscription(subscription);
        listener.onSubscriptionUpdate(SubscriptionsGlobal.getInstance().getSubscriptionsCount());
    }

    @Override
    public void onLoadAllSubscriptions(List<Subscription> subscriptions) {

    }

    public void onLoad(Card card) {
        adapter.addCard(card);
        listener.cardsCount(adapter.getItemCount());
        hideKeyboard(getView());
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDelete(String subscriptionID) {
        adapter.updateCard(SubscriptionsGlobal.getInstance().getCardIdFrom(subscriptionID));
        SubscriptionsGlobal.getInstance().deleteSubscription(subscriptionID);
        listener.onSubscriptionUpdate(SubscriptionsGlobal.getInstance().getSubscriptionsCount());
    }

    @Override
    public void onLoad(Subscription subscription) {

    }

    @Override
    public void onLoadAllCards(List<Card> items) {

    }

    @Override
    public void onError(DataStoreError error) {
        Toast.makeText(getActivity(), R.string.error_searching_card, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSave(Card item) {

    }

    public interface OnSearchHaveResults{
        void cardsCount(int count);
        void onSubscriptionUpdate(int count);
    }

}