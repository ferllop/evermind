package com.ferllop.evermind.repositories.datasource;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Labelling;
import com.ferllop.evermind.repositories.DatastoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreCardDataSource extends FirestoreDataSource<Card> {

    public FirestoreCardDataSource(DatastoreListener<Card> listener) {
        super(Card.class, listener);
    }

    @Override
    protected Card fromMap(String id, Map map) {
        String author = (String) map.get("author");
        String question = (String) map.get("question");
        String answer = (String) map.get("answer");
        Map<String, Boolean> rawLabels = (Map<String, Boolean>) map.get("labels");
        Labelling labelling = new Labelling(booleanMapToList(rawLabels));
        return new Card(id, author, question, answer, labelling);
    }

    @Override
    protected Map<String, Object> toMap(Card card) {
        Map<String, Object> result = new HashMap<>();
        result.put("author", card.getAuthor());
        result.put("question", card.getQuestion());
        result.put("answer", card.getAnswer());
        result.put("labels", listToBooleanMap(card.getLabelling().getLabels()));
        return result;
    }


    private List<String> booleanMapToList(Map<String, Boolean> rawLabels) {
        List<String> result = new ArrayList<>();
        for(Map.Entry<String, Boolean> label : rawLabels.entrySet()){
            result.add(label.getKey());
        }
        return result;
    }


    private Map<String, Boolean> listToBooleanMap(List<String> labels) {
        Map<String, Boolean> result = new HashMap<>();
        for(String label : labels){
            if(!label.trim().isEmpty()) {
                result.put(label.trim(), true);
            }
        }
        return result;
    }


}
