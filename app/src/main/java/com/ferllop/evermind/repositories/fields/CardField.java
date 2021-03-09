package com.ferllop.evermind.repositories.fields;

public enum CardField {
    ID("id"),
    AUTHOR_ID("authorID"),
    AUTHOR_USERNAME("authorUsername"),
    QUESTION("question"),
    ANSWER("answer"),
    LABELLING("labels"),
    LABEL_LIST_SEPARATOR(",");

    private final String value;

    CardField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
