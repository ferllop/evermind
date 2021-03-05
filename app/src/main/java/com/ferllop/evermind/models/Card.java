package com.ferllop.evermind.models;

import java.util.ArrayList;
import java.util.List;

public class Card extends Model{

    protected String author;
    protected String question;
    protected String answer;
    protected List<String> labels;

    private Card(){
        this.author = "anonymous";
    }

    public Card(String author, String question, String answer, String labels){
        this.author = author;
        this.question = question;
        this.answer = answer;
        this.labels = listifyLabels(labels);
    }

    public Card(String id, String author, String question, String answer, List<String> labels){
        this.setId(id);
        this.author = author;
        this.question = question;
        this.answer = answer;
        this.labels = labels;

    }

    public String getAuthor() {
        return author;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getLabels() {
        return labels;
    }

    public String stringifyLabels(){
        if(labels == null){
            return "unlabeled";
        }
        String result = "";
        for (String label : labels){
            result = result + label + ", ";
        }
        return result.substring(0, result.length() -2);
    }

    public List<String> listifyLabels(String labels){
        List<String> result = new ArrayList<>();
        for(String label : labels.toLowerCase().split(",")){
            if(!label.trim().isEmpty()) {
                result.add(label.trim());
            }
        }
        return result;
    }
}
