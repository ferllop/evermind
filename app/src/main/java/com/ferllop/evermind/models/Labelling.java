package com.ferllop.evermind.models;

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
        for(String label : labels.toLowerCase().split(",")){
            if(!label.trim().isEmpty()) {
                result.add(label.trim());
            }
        }
        return result;
    }

    public List<String> getLabels(){
        return labels;
    }
}
