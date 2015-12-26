package com.example.itspm.cardsagainstus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game extends AppCompatActivity {
    private ArrayList<Card> blackCards;
    private ArrayList<Card> whiteCards;
    private ArrayList<Player> players;
    private Player czar;
    private int turn;
    private int whiteCardNumber;
    private int blackCardNumber;
    private Context context;

    public int minPlayers = 2;
    public int maxScore = 5;

    private long randomSeed;

    public Game(Context context, ArrayList<Player> players){
        this.context = context;
        this.players = players;
        this.blackCards = new ArrayList<>();
        this.whiteCards = new ArrayList<>();
        getCards();
        this.randomSeed = System.nanoTime();
        Collections.shuffle(this.players, new Random(this.randomSeed));
        this.czar = this.players.get(0);
        this.turn = 0;
        this.whiteCardNumber = 0;
        this.blackCardNumber = 0;
        drawCards();
    }

    private void getCards(){
        try{
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(readJson("cards.json"));
            for(Object object: array){
                JSONObject card = (JSONObject) object;
                int id = (int)(long) card.get("id");
                String cardType = (String) card.get("cardType");
                String text = (String) card.get("text");
                int numAnswers = (int)(long)  card.get("numAnswers");
                String expansion = (String) card.get("expansion");

                Card newCard = new Card(id, cardType, text, numAnswers, expansion);
                if(cardType.equals("Q")){
                    this.blackCards.add(newCard);
                }else{
                    this.whiteCards.add(newCard);
                }
            }
            this.randomSeed = System.nanoTime();
            Collections.shuffle(this.blackCards, new Random(this.randomSeed));
            this.randomSeed = System.nanoTime();
            Collections.shuffle(this.whiteCards, new Random(this.randomSeed));
        }catch (Exception e){
            e.printStackTrace();
            return ;
        }
    }

    private String readJson(String path){
        try{
            InputStream json = context.getAssets().open(path);
            int size = json.available();
            byte[] buffer = new byte[size];
            json.read(buffer);
            json.close();
            String jsonString = new String(buffer, "UTF-8");
            return jsonString;
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

    public void drawCards(){
        for(Player player: this.players){
            for(int i=player.getDeck().size(); i<player.getMaxCards(); i++){
                player.addCard(this.whiteCards.get(this.whiteCardNumber));
                System.out.println(player.getName() + " has drawn " + this.whiteCards.get(this.whiteCardNumber));
                this.whiteCardNumber++;
            }
        }
    }

    public Card drawQuestion(){
        this.czar.setQuestion(this.blackCards.get(this.blackCardNumber));
        this.blackCardNumber++;
        return this.czar.getQuestion();
    }

    public void chooseAnswers(Card question){
        for(Player player: this.players){
            if(player != this.czar){
                this.randomSeed = System.nanoTime();
                Random rand = new Random(randomSeed);
                int randInt = rand.nextInt(player.getMaxCards());
                ArrayList<Card> cardsToPlay = new ArrayList();
                for(int i=0; i<question.getNumAnswers(); i++){
                    cardsToPlay.add(player.getDeck().get((randInt) % (player.getMaxCards()-1)));
                    randInt++;
                    System.out.println(player.getName() + " has played : " + cardsToPlay.get(i).toString());
                }
                player.playCards(cardsToPlay);
            }
        }
    }

    public void chooseWinner(){
        this.randomSeed = System.nanoTime();
        Random rand = new Random(randomSeed);
        int randInt = rand.nextInt(getPlayers().size());
        Player winner;
        if(getCzar() == getPlayers().get(randInt)){
            winner = getPlayers().get((randInt+1) % (getPlayers().size()));
        }
        else{
            winner = getPlayers().get(randInt);
        }
        winner.win();
        System.out.println("-- " + getCzar().getName() + " elected " + winner.getName() + " as the winner!");
        System.out.println("---- " + winner.getName() + " has " + winner.getScore() + " points.");
    }

    public void rotateCzar(){
        for(int i=0;i<getPlayers().size();i++){
            if(getPlayers().get(i) == getCzar()){
                setCzar(getPlayers().get((i+1) % (getPlayers().size())));
                break;
            }
        }
        System.out.println("-- " + getCzar().getName() + " is now the new czar.");
    }

    public void newTurn(){
        this.turn++;
        System.out.println("------------------------");
        System.out.println("-------- Turn " + this.turn + " --------");
        System.out.println("------------------------");
        Card question = drawQuestion();
        System.out.println("-- Question: " + question.toString());
        chooseAnswers(question);
        chooseWinner();
        rotateCzar();
        drawCards();
    }

    public void getPlayerScores(){
        String playersString = "";
        for(Player player: this.players){
            playersString += player.getName() + " has " + player.getScore() + " points!\n";
        }
        System.out.println(playersString);
    }

    //toString

    @Override
    public String toString() {
        //return super.toString();
        String gameString = "";
        for(Player player: this.players){
            gameString += player.toString();
        }
        return gameString;
    }

    //Gets and Sets

    public int getHighestScore(){
        int highestScore = 0;
        for(Player player: this.players){
            if(player.getScore()>highestScore){
                highestScore = player.getScore();
            }
        }
        return highestScore;
    }

    public Player getHighestScorePlayer(){
        int highestScore = 0;
        Player highestScorePlayer = this.players.get(0);
        for(Player player: this.players){
            if(player.getScore()>highestScore){
                highestScore = player.getScore();
                highestScorePlayer = player;
            }
        }
        return highestScorePlayer;
    }

    public ArrayList<Card> getBlackCards() {
        return blackCards;
    }

    public void setBlackCards(ArrayList<Card> blackCards) {
        this.blackCards = blackCards;
    }

    public ArrayList<Card> getWhiteCards() {
        return whiteCards;
    }

    public void setWhiteCards(ArrayList<Card> whiteCards) {
        this.whiteCards = whiteCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getCzarName() {
        return czar.getName();
    }

    public Player getCzar() {
        return czar;
    }

    public void setCzar(Player czar) {
        this.czar = czar;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getCardNumber() {
        return whiteCardNumber;
    }

    public void setCardNumber(int whiteCardNumber) {
        this.whiteCardNumber = whiteCardNumber;
    }
}
