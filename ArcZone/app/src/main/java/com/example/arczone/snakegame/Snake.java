package com.example.arczone.snakegame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.arczone.universal.universal_methods;

public class Snake extends universal_methods {

    SnakeController snakeController;

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
    }

    @Override
    protected void onResume(){
        super.onResume();
        snakeController.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        snakeController.pause();
    }
}