package com.ferllop.evermind.models;

public class Card extends Model{

    protected String authorID;
    protected String authorUsername;
    protected String question;
    protected String answer;
    protected Labelling labelling;

    public Card(String authorID, String authorUsername, String question, String answer, String labels){
        this.authorID = authorID;
        this.authorUsername = authorUsername;
        this.question = question;
        this.answer = answer;
        this.labelling = new Labelling(labels);
    }

    public Card(String id, String authorID, String authorUsername, String question, String answer, Labelling labelling){
        this.setId(id);
        this.authorID = authorID;
        this.authorUsername = authorUsername;
        this.question = question;
        this.answer = answer;
        this.labelling = labelling;
    }

    public Card clone(){
        return new Card(getAuthorID(), getAuthorUsername(), getQuestion(), getAnswer(), getLabelling().toString());
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Labelling getLabelling() {
        return labelling;
    }

}
