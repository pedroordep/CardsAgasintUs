package com.example.itspm.cardsagainstus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGame();
        startGame();
    }

    public void initGame(){
        Player player1 = new Player("Pedro");
        Player player2 = new Player("Paulo");
        Player player3 = new Player("Brás");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        game = new Game(getApplicationContext(),players);
        System.out.println("Game ready. The czar is: " + game.getCzarName());
    }

    public void startGame(){
        System.out.println(game.toString());
        while(game.getHighestScore()<game.maxScore){
            game.newTurn();
        }
        System.out.println("----- The winner is " + game.getHighestScorePlayer().getName() + " with " + game.getHighestScorePlayer().getScore() + " points!");
        game.getPlayerScores();
    }
}
