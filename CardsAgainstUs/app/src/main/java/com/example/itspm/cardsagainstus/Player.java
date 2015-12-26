package com.example.itspm.cardsagainstus;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> deck;
    private Card question;
    private int score;
    private int maxCards;

    public Player(String name){
        this.name = name;
        this.deck = new ArrayList<>();
        this.question = null;
        this.score = 0;
        this.maxCards = 10;
    }

    public ArrayList<Card> playCards(ArrayList<Card> cards){
        for(Card card: cards)
            this.deck.remove(card);
        return cards;
    }

    public boolean addCard(Card card){
        if(this.deck.size()<this.maxCards){
            this.deck.add(card);
            return true;
        }
        return false;
    }

    public boolean removeCard(Card card){
        if(this.deck.size()>0){
            this.deck.remove(card);
            return true;
        }
        return false;
    }

    public int win(){
        this.score++;
        return this.score;
    }

    //toString

    @Override
    public String toString() {
        //return super.toString();
        String deckString = "";
        for(Card card: deck){
            deckString += "[" + card.getId() + "] " + card.getText() + "\n";
        }
        return this.name + " has " + this.score + " points and has this deck:\n" + deckString;
    }

    //Gets and Sets

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxCards() {
        return maxCards;
    }

    public void setMaxCards(int maxCards) {
        this.maxCards = maxCards;
    }

    public Card getQuestion() {
        return question;
    }

    public void setQuestion(Card question) {
        this.question = question;
    }
}
