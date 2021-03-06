package com.ferllop.evermind.models;

public class Card extends Model{

    protected String author;
    protected String question;
    protected String answer;
    protected Labelling labelling;

    public Card(String author, String question, String answer, String labels){
        this.author = author;
        this.question = question;
        this.answer = answer;
        this.labelling = new Labelling(labels);
    }

    public Card(String id, String author, String question, String answer, Labelling labelling){
        this.setId(id);
        this.author = author;
        this.question = question;
        this.answer = answer;
        this.labelling = labelling;
    }

    public Card clone(){
        return new Card(getAuthor(), getQuestion(), getAnswer(), getLabelling().toString());
    }

    public String getAuthor() {
        return author;
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
