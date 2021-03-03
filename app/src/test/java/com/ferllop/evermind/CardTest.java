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
        Card card = new Card("_", "_", "_", inputText);

        Map<String,Boolean> labelsAsMap = new HashMap<>();
        labelsAsMap.put("label1", true);
        labelsAsMap.put("label2", true);
        labelsAsMap.put("label3", true);

        assertEquals(labelsAsMap, card.getLabels());
    }

    @Test
    public void labels_can_be_stringified_in_a_comma_separated_string() {
        Card card = new Card("_", "_", "_", "label1, label2, label3");
        String expected = "label1, label2, label3";
        assertEquals(expected, card.stringifyLabels());
    }

    @Test
    public void wrong_labels_in_comma_separated_list_will_be_ignored() {
        Card card = new Card("_", "_", "_", ",label1, ,label2, label3,");
        String expected = "label1, label2, label3";
        assertEquals(expected, card.stringifyLabels());
    }

}