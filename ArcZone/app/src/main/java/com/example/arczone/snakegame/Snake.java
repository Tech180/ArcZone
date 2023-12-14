package com.example.arczone.snakegame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.arczone.universal.SettingsOverlay;
import com.example.arczone.universal.universal_methods;

public class Snake extends universal_methods {

    SnakeController snakeController;

    private SettingsOverlay settingsOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeTitleBar(this);

        //get dimensions of the screen
        Display screen = getWindowManager().getDefaultDisplay();

        //init the score into a point object
        Point size = new Point();
        screen.getSize(size);

        //instantiate snakeController
        snakeController = new SnakeController(this, size);

        setContentView(snakeController);

        settingsOverlay = new SettingsOverlay();

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, settingsOverlay).addToBackStack(null).commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        snakeController.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        snakeController.pause();
    }
}