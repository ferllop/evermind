package com.ferllop.evermind.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {

    protected String question;
    protected String answer;
    protected List<String> labels;

    public Card(){}

    public Card(String question, String answer, String labels){
        this.question = question;
        this.answer = answer;
        this.labels = Card.parseLabels(labels);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void NO_setLabels(List<String> labels) {
        this.labels = labels;
    }

    public void setLabels(Map<String, Boolean> labels) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Boolean> label : labels.entrySet()){
            result.add(label.getKey());
        }
        this.labels = result;
    }

    public static List<String> parseLabels(String labels) {
        return Arrays.asList(labels.toLowerCase().trim().split(","));
    }

    public Map<String, Boolean> getlabelsAsMap(){
        Map<String,Boolean> result = new HashMap<>();
        for(String label: this.labels){
            result.put(label, true);
        }
        return result;
    }
}
