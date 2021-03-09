package com.ferllop.evermind;

import org.junit.Test;

import java.util.Map;

public class CardFirestoreRepository {
    class FakeFirestore<T> {
        Map<String, Object> db;

        public FakeFirestore(Map<String, Object> db) {
            this.db = db;
        }

        public Map<String, Object> collection(String){

        }
    }
    @Test
    public void should_insert_a_card_object(){

    }
}
