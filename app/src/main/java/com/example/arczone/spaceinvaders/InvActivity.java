package com.example.arczone.spaceinvaders;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.arczone.universal.SettingsInterface;
import com.example.arczone.universal.SettingsOverlay;
import com.example.arczone.universal.universal_methods;

public class InvActivity extends universal_methods implements SettingsInterface {

    SpInView SpInView;
    float difficulty;

    private SettingsOverlay settingsOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        removeTitleBar(this);

        Display dis = getWindowManager().getDefaultDisplay();
        Point s = new Point();
        dis.getSize(s);
        SpInView = new SpInView(this, s.x, s.y, difficulty);
        setContentView(SpInView);

        settingsOverlay = new SettingsOverlay();

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, settingsOverlay).addToBackStack(null).commit();
    }

    public void onResume(){
        super.onResume();
        SpInView.resume(difficulty);
    }

    public void onPause(){
        super.onPause();
        SpInView.pause();
    }

    @Override
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setMusicEffects(int musicEffects) {

    }
}
