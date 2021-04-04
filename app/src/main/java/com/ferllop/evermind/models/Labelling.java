package com.ferllop.evermind.models;

import com.ferllop.evermind.repositories.fields.CardField;

import java.util.ArrayList;
import java.util.List;

public class Labelling {
    List<String> labels;

    public Labelling(List<String> labels) {
        this.labels = labels;
    }

    public Labelling(String labels) {
        this.labels = toList(labels);
    }

    @Override
    public String toString(){
        if(labels == null){
            return "unlabeled";
        }
        String result = "";
        for (String label : labels){
            result = result.concat(label + ", ");
        }
        return result.substring(0, result.length() -2);
    }

    private List<String> toList(String labels){
        List<String> result = new ArrayList<>();
        for(String label : labels.toLowerCase().split(CardField.LABEL_LIST_SEPARATOR.getValue())){
            if(!label.trim().isEmpty()) {
                result.add(label.trim().replace(' ', '-'));
            }
        }
        return result;
    }

    public List<String> getLabels(){
        return labels;
    }
}
