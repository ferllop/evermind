package com.ferllop.evermind.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    String question;
    String answer;
    List<String> labels;

    public Card(String question, String answer, List<String>labels){
        this.question = question;
        this.answer = answer;
        this.labels = labels;
    }

    public static List<String> parseLabels(String labels) {
        return Arrays.asList(labels.trim().split(","));
    }
}
