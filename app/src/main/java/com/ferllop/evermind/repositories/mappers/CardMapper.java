package com.ferllop.evermind.repositories.mappers;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Labelling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardMapper implements ModelMapper<Card>{
    public Card execute(String id, Map map) {
        String author = (String) map.get("author");
        String question = (String) map.get("question");
        String answer = (String) map.get("answer");
        Map<String, Boolean> rawLabels = (Map<String, Boolean>) map.get("labels");
        Labelling labelling = new Labelling(booleanMapToList(rawLabels));
        return new Card(id, author, question, answer, labelling);
    }

    public Map<String, Object> execute(Card card) {
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
