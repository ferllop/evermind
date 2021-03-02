package com.ferllop.evermind.models;

import java.util.Arrays;
import java.util.List;

public class Card {

    protected String question;
    protected String answer;
    protected List<String> labels;

    public Card(){}

    public Card(String question, String answer, List<String> labels){
        this.question = question;
        this.answer = answer;
        this.labels = labels;
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

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public static List<String> parseLabels(String labels) {
        return Arrays.asList(labels.trim().split(","));
    }
}
