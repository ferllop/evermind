package com.ferllop.evermind;

import com.ferllop.evermind.models.Card;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void labels_should_be_saved_in_lowercase(){
        Card lowerCaseCard = new Card("_", "_", "_", "label1,label2,label3");
        Card upperCaseCard = new Card("_", "_", "_", "LABEL1,LABEL2,LABEL3");
        assertEquals(lowerCaseCard.getLabels(), upperCaseCard.getLabels());
    }

    @Test
    public void labels_should_be_provided_as_comma_separated_text() {
        String inputText = "label1,label2,label3";
        List<String> labels = Card.parseLabels(inputText);
        assertEquals(Arrays.asList("label1", "label2", "label3"), labels);
    }

    @Test
    public void should_set_labels_as_array_when_a_map_is_provided() {
        Card card = new Card("_", "_", "_", "label1,label2,label3");
        Map<String,Boolean> labelsAsMap = new HashMap<>();
        labelsAsMap.put("label1", true);
        labelsAsMap.put("label2", true);
        labelsAsMap.put("label3", true);
        Card cardWithMap = new Card("_", "_", "_", "_");
        cardWithMap.setLabels(labelsAsMap);
        assertEquals(card.getLabels(), cardWithMap.getLabels());
    }
}