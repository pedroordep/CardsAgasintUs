package com.example.itspm.cardsagainstus;

public class Card {
    private int id;
    private String type;
    private String text;
    private int numAnswers;
    private String expansion;

    public Card(int id, String type, String text, int numAnswers, String expansion){
        this.id = id;
        this.type = type;
        this.text = text;
        this.numAnswers = numAnswers;
        this.expansion = expansion;
    }

    //toString

    @Override
    public String toString() {
        //return super.toString();
        return "[" + this.getId() + "] " + this.getText();
    }

    //Gets and Sets

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumAnswers() {
        return numAnswers;
    }

    public void setNumAnswers(int numAnswers) {
        this.numAnswers = numAnswers;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }
}
