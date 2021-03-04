package com.ferllop.evermind.models;

import java.util.HashMap;
import java.util.Map;

public class Card extends Model{

    protected String author;
    protected String question;
    protected String answer;
    protected Map<String, Boolean> labels;

    public Card(){
        this.author = "anonymous";
    }

    public Card(String author, String question, String answer, String labels){
        this.author = author;
        this.question = question;
        this.answer = answer;
        this.labels = this.mapLabels(labels);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Map<String, Boolean> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, Boolean> labels) {
        this.labels = labels;
    }

    public String stringifyLabels(){
        if(this.labels == null){
            return "unlabeled";
        }

        String result = "";
        for (Map.Entry<String, Boolean> label : this.labels.entrySet()){
            result = result + label.getKey() + ", ";
        }
        return result.substring(0, result.length() -2);
    }

    public Map<String, Boolean> mapLabels(String labels) {
        Map<String, Boolean> result = new HashMap<>();
        for(String label : labels.toLowerCase().split(",")){
            if(!label.trim().isEmpty()) {
                result.put(label.trim(), true);
            }
        }
        return result;
    }

}
