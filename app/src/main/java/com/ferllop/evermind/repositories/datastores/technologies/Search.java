package com.ferllop.evermind.repositories.datastores.technologies;

import com.ferllop.evermind.repositories.fields.CardField;
import com.ferllop.evermind.repositories.fields.UserField;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class Search {
    Token[] tokens;

    public Search(String query){
        if (Strings.isNullOrEmpty(query)){
            throw new IllegalArgumentException("a valid query must be provided");
        }
        tokens = this.extractTokens(query.toLowerCase());
    }

    private Token[] extractTokens(String query){
        List<Token> labels = new ArrayList<>();
        for(String queryPart : parseQuery(query)){
            labels.add(new Token(queryPart));
        }
        return labels.toArray(new Token[labels.size()]);
    }

    private String[] parseQuery(String query){
        return query.trim().split(CardField.LABEL_LIST_SEPARATOR.getValue());
    }

    private Token getFirstAuthorToken(){
        for(Token token : tokens){
            if(token.isAuthor()){
                return token;
            }
        }
        return null;
    }

    private Token[] getLabelTokens(){
        List<Token> labels = new ArrayList<>();
        for(Token token : tokens){
            if(token.isLabel()){
                labels.add(token);
            }
        }
        return labels.toArray(new Token[labels.size()]);
    }

    public List<String> getLabels(){
        List<String> labels = new ArrayList<>();
        for(Token token : getLabelTokens()){
            labels.add(token.toString());
        }
        return labels;
    }

    public String getAuthor(){
        return this.getFirstAuthorToken().toString().replace(UserField.USER_PREFIX.getValue(), "");
    }

    public boolean hasLabels(){
        return this.getLabels().size() > 0;
    }

    public boolean hasAuthor(){
        return getFirstAuthorToken() != null;
    }

    class Token{
        String value;

        public Token(String token){
            this.value = token;
        }

        public boolean isAuthor(){
            return value.startsWith(UserField.USER_PREFIX.getValue());
        }

        public boolean isLabel(){
            return !this.isAuthor();
        }

        @Override
        public String toString(){
            return value;
        }
    }
}
