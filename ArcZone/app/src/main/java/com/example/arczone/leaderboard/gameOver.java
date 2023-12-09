package com.example.arczone.leaderboard;

import android.content.Intent;
import android.os.Bundle;

import com.example.arczone.R;
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
    }
}