package com.example.arczone.spaceinvaders;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class InvActivity extends Activity {

    SpInView SpInView;
    float volume = 1;
    float difficulty = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Display dis = getWindowManager().getDefaultDisplay();
        Point s = new Point();
        dis.getSize(s);
        volume = getVolumeControlStream();
        SpInView = new SpInView(this, s.x, s.y,difficulty,volume);
        setContentView(SpInView);
    }

    protected void onResume(){
        super.onResume();
        volume = getVolumeControlStream();
        SpInView.resume(difficulty, volume);
    }

    protected void onPause(){
        super.onPause();
        SpInView.pause();
    }
}
