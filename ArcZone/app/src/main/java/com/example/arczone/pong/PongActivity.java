package com.example.arczone.pong;

import static com.example.arczone.universal.universal_methods.gameOver;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arczone.R;
import com.example.arczone.universal.SettingsInterface;
import com.example.arczone.universal.SettingsOverlay;

public class PongActivity extends AppCompatActivity {

    private PongGameView pongGameView;

    private Button startButton;
    private TextView scoreUser, scoreOpponent;

    private SettingsOverlay settingsOverlay;
    //private TextView gameOver;

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
        settingsOverlay = new SettingsOverlay();

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, settingsOverlay).addToBackStack(null).commit();


        // Assign scores
        pongGameView.opponentScore = scoreOpponent;
        pongGameView.userScore = scoreUser;
    }

    /**
     * Method called when the game is over.
     * Displays the game over message and restart button after a delay.
     */
    public void gameOverPong() {

        pongGameView.playing = false;

        Integer score = pongGameView.scoreUser;

        pongGameView.scoreUser = 0;
        pongGameView.scoreOpponent = 0;

        //startButton = findViewById(R.id.startButton);

        //gameOver.setVisibility(View.VISIBLE);

        gameOver(pongGameView.context, "Snake", score, PongActivity.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        pongGameView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pongGameView.pause();
    }
}
