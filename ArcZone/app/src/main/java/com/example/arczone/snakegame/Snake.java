package com.example.arczone.snakegame;

import static com.example.arczone.universal.universal_methods.removeTitleBar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.arczone.R;

public class Snake extends AppCompatActivity {

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

        setContentView(R.layout.activity_snake);
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