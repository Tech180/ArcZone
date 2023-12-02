package com.example.arczone.games;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Pong extends Activity {

    private PongGameView pongGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Create the PongGameView
        pongGameView = new PongGameView(this);
        setContentView(pongGameView);
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
    }
}
