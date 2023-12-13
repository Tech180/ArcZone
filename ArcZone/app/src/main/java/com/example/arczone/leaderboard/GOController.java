package com.example.arczone.leaderboard;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.arczone.R;
import com.example.arczone.firebase.ArcZoneDatabase;
import com.example.arczone.firebase.ArcZoneUser;
import com.example.arczone.universal.universal_methods;

import java.util.HashMap;
import java.util.Map;

public class GOController extends universal_methods {
    private AppCompatActivity activity;
    private ArcZoneUser user;
    private ArcZoneDatabase db;
    private String game;
    private Integer score;
    private Map<String, Integer>[] scores;

    private TextView highscore;
    private TextView finalscore;
    private TextView gameover;
    private Button leaderButton;
    private int mode = -1;
    public GOController(AppCompatActivity activity, String game, Integer score){
        this.activity = activity;
        this.game = game;
        this.score = score;

        this.user = ArcZoneUser.getInstance();
        this.db = new ArcZoneDatabase();

        setViews();
        setScore();
    }

    private void setScore(){

        flashAnimation(gameover);
        flashAnimation(leaderButton);

        if(game.equals("Pong")) mode = 0;
        else if(game.equals("Snake")) mode = 1;
        else mode = 2;

        scores = user.getScores();

        //if user is not guest, set the high score and update it in the DB if its higher
        if(user.getUsername() != "Guest") {
            highscore.setText("High Score: " +
                    scores[mode].get(game));

            //if new score is higher than stored high score
            if(scores[mode].get(game) < this.score) {
                //put score into map object
                Map<String, Integer> newScore = new HashMap<>();
                newScore.put(game, this.score);

                //update score in db
                db.updateUserScore(newScore, user);
            }
        }else{
            highscore.setVisibility(View.INVISIBLE);
        }
        finalscore.setText("Final Score: " + this.score);

        leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeaderboard(game, score);
            }
        });
    }

    private void setViews(){
        gameover = activity.findViewById(R.id.gameover);
        highscore = activity.findViewById(R.id.high_score);
        finalscore = activity.findViewById(R.id.final_score);
        leaderButton = activity.findViewById(R.id.leaderboardButton);
    }

    private void showLeaderboard(String game, int newScore){
        Leaderboard frag = new Leaderboard(game, newScore);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the container with your fragment
        fragmentTransaction.replace(R.id.go_lb, frag);

        // Add the transaction to the back stack
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }
}
