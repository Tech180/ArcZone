package com.example.arczone.snakegame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.arczone.universal.SettingsInterface;
import com.example.arczone.universal.SettingsOverlay;
import com.example.arczone.universal.universal_methods;

public class Snake extends universal_methods implements SettingsInterface {

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
        setDifficulty(0);
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

    @Override
    public void setDifficulty(int difficulty){
        if(difficulty == 0) {
            this.snakeController.FPS = 5;
        }
        if(difficulty == 1) {
            this.snakeController.FPS = 10;
        }
        if(difficulty == 2) {
            this.snakeController.FPS = 15;
        }
    }

    @Override
    public void setMusicEffects(int musicEffects) {
        snakeController.soundPool.setVolume(snakeController.death, musicEffects, musicEffects);
        snakeController.soundPool.setVolume(snakeController.eat_apple, musicEffects, musicEffects);
    }
}