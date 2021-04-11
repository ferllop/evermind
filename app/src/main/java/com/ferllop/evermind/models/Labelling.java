package com.ferllop.evermind.models;

import android.util.Log;

import com.ferllop.evermind.repositories.fields.CardField;

import java.util.ArrayList;
import java.util.List;

public class Labelling {
    List<String> labels;
    private final String TAG = "MYAPP-Labelling";

    public Labelling(List<String> labels) {
        for(String label: labels){
            if(!isValid(label)) {
                Log.d(TAG, "Label: not valid: " + label);
                throw new AssertionError();
            }
        }
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
            result = result.concat(label + CardField.LABEL_LIST_SEPARATOR.getValue() + " ");
        }
        return result.substring(0, result.length() -2);
    }

    private List<String> toList(String labels){
        List<String> result = new ArrayList<>();
        for(String label : labels.toLowerCase().split(CardField.LABEL_LIST_SEPARATOR.getValue())){
            if(!label.trim().isEmpty()) {
                if(!isValid(label.trim())) throw new AssertionError();
                result.add(label.trim());
            }
        }
        return result;
    }

    public List<String> getLabels(){
        return labels;
    }

    public static boolean isValid(String labelling){
        return !labelling.matches(".*[^-,\\w].*");
    }
}
