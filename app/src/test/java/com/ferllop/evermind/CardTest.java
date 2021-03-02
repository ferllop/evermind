package com.ferllop.evermind;

import com.ferllop.evermind.models.Card;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CardTest {
    @Test
    public void labels_should_be_provided_as_comma_separated_text() {
        String inputText = "label1,label2,label3";
        List<String> labels = Card.parseLabels(inputText);
        assertEquals(Arrays.asList("label1", "label2", "label3"), labels);
    }
}