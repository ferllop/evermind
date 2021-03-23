package com.ferllop.evermind.repositories.listeners;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.repositories.fields.DataStoreError;

import java.util.List;

public interface CardDataStoreListener {
    void onLoad(Card card);
    void onDelete(String id);
    void onError(DataStoreError error);
    void onSave(Card card);
    void onLoadAllCards(List<Card> cards);

}
