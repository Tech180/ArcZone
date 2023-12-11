package com.example.arczone.pong;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arczone.R;

public class PongActivity extends AppCompatActivity {

    private PongGameView pongGameView;

    private static Button startButton;
    private TextView scoreUser, scoreOpponent;

    //private SettingsOverlay settingsOverlay;
    private TextView gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_pong);

        // Initialize game view and UI
        pongGameView = findViewById(R.id.pongGameView);
        pongGameView.setPongActivity(this);

        scoreUser = findViewById(R.id.scoreUser);
        scoreOpponent = findViewById(R.id.scoreOpponent);


        // Initialize start button and game over
        startButton = findViewById(R.id.startButton);
        gameOver = findViewById(R.id.gameOver);

        scoreOpponent = findViewById(R.id.scoreOpponent);
        scoreUser = findViewById(R.id.scoreUser);

        // Set click listener for the start button to initiate the game
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pongGameView.startGame();
                startButton.setVisibility(View.GONE);

            }
        });
        // Create and initialize settings overlay with visibility
        //settingsOverlay = new SettingsOverlay(true);


        // Assign scores
        pongGameView.opponentScore = scoreOpponent;
        pongGameView.userScore = scoreUser;



    }

    /**
     * Method called when the game is over.
     * Displays the game over message and restart button after a delay.
     */
    public void gameOver() {

        pongGameView.playing = false;

        startButton = findViewById(R.id.startButton);

        gameOver.setVisibility(View.VISIBLE);

        // Hide the game over TextView after three seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gameOver.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

    @Override
    protected void onResume() {
        super.onResume();
        pongGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pongGameView.pause();

        //settingsOverlay.hideOverlay();
    }
}
