package com.ferllop.evermind;

import com.ferllop.evermind.repositories.datastores.technologies.Search;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchTest {

    @Test(expected = Exception.class)
    public void should_accept_only_queries_with_at_least_one_character(){
        Search search = new Search("");
    }

    @Test
    public void should_be_able_to_provide_the_author_when_a_word_prefixed_with_at_symbol_is_provided_alone(){
        Search search = new Search("@author");
        assertEquals(search.getAuthor(), "author");
    }

    @Test
    public void sshould_be_able_to_provide_the_author_when_a_word_prefixed_with_at_symbol_is_provided_in_a_comma_separated_list(){
        Search search = new Search("@author,label1");
        assertEquals(search.getAuthor(), "author");
    }

    @Test
    public void should_be_able_to_provide_only_the_first_author_when_multiple_authors_are_provided(){
        Search search = new Search("@author1,@author2");
        assertEquals(search.getAuthor(), "author1");
    }

    @Test
    public void should_be_able_to_provide_a_label_when_a_word_is_not_prefixed_with_at_symbol(){
        Search search = new Search("label");
        assertEquals(search.getLabels().get(0), "label");
    }

    @Test
    public void should_be_able_to_provide_all_labels_when_multiple_words_withouth_at_symbol_are_provided(){
        Search search = new Search("label1,label2,label3");
        assertEquals(search.getLabels(), Arrays.asList("label1", "label2", "label3"));
    }

    @Test
    public void should_be_able_to_provide_only_labels_when_an_author_and_labels_are_provided(){
        Search search = new Search("label1,@author,label2");
        assertEquals(search.getLabels(), Arrays.asList("label1", "label2"));
    }

    @Test
    public void should_confirm_that_has_labels(){
        Search search = new Search("label1,@author,label2");
        assertTrue(search.hasLabels());
    }

    @Test
    public void should_confirm_that_not_has_labels(){
        Search search = new Search("@author");
        assertFalse(search.hasLabels());
    }

    @Test
    public void should_confirm_that_has_author(){
        Search search = new Search("label1,@author,label2");
        assertTrue(search.hasAuthor());
    }

    @Test
    public void should_confirm_that_not_has_author(){
        Search search = new Search("label1,label2");
        assertFalse(search.hasAuthor());
    }

    @Test
    public void label_tokens_should_be_case_insensitive_(){
        Search lowerCaseSearch = new Search("label1,label2");
        Search mixedCaseSearch = new Search("LABel1,labEL2");

        assertEquals(lowerCaseSearch.getLabels(), mixedCaseSearch.getLabels());
    }

    @Test
    public void author_tokens_should_be_case_insensitive_(){
        Search lowerCaseSearch = new Search("@author");
        Search mixedCaseSearch = new Search("@AUthor");

        assertEquals(lowerCaseSearch.getAuthor(), mixedCaseSearch.getAuthor());
    }


}
