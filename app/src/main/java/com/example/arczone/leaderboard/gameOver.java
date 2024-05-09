package com.example.arczone.leaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.arczone.R;
import com.example.arczone.universal.SettingsOverlay;
import com.example.arczone.universal.universal_methods;

public class gameOver extends universal_methods {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeTitleBar(this);

        setContentView(R.layout.activity_go_lb);

        intent = getIntent();

        new GOController(this, intent.getStringExtra("game"), intent.getIntExtra("score", 0));

        String className = intent.getStringExtra("gameClass");

        Class<?> gameClass = null;
        
        try {
            gameClass = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        intentPass(gameClass);

    }

    void intentPass(Class<?> gameClass ) {

        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent restart = new Intent(gameOver.this, gameClass);
                restart.putExtra("game", intent.getStringExtra("game"));
                startActivity(restart);

                finish();
            }
        });
    }
}