package com.ferllop.evermind;

import com.ferllop.evermind.models.Card;
import com.ferllop.evermind.models.Labelling;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class LabellingTest {

    @Test
    public void labels_should_be_saved_in_lowercase(){
        Labelling upperCaseLabel = new Labelling("LABEL1");
        assertEquals("label1", upperCaseLabel.getLabels().get(0));
    }

    @Test
    public void labels_can_be_retrieved_as_a_comma_separated_text_list() {
        Labelling labelling = new Labelling("label1, label2, label3");
        assertEquals("label1, label2, label3", labelling.toString());
    }

    @Test
    public void wrong_labels_in_comma_separated_list_will_be_ignored() {
        Labelling wrongLabelling = new Labelling(",,,label1, ,label2, label3,,,");
        assertEquals("label1, label2, label3", wrongLabelling.toString());
    }

    @Test(expected = AssertionError.class)
    public void wrong_characters_in_a_label_throw_assert_error(){
        Labelling wrongLabel = new Labelling("label#1");
    }

    @Test(expected = AssertionError.class)
    public void wrong_characters_in_a_label_contained_in_a_comma_separated_list_will_throw_assert_error(){
        Labelling wrongLabel = new Labelling("label1 label2");
    }

    @Test(expected = AssertionError.class)
    public void wrong_characters_in_a_label_contained_in_list_object_will_throw_assert_error(){
        Labelling wrongLabel = new Labelling(Arrays.asList("label#1", "label2"));
    }
}
