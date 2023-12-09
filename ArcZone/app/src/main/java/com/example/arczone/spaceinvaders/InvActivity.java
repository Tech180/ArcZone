package com.example.arczone.spaceinvaders;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class InvActivity extends Activity {

    SpInView SpInView;
    float difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Display dis = getWindowManager().getDefaultDisplay();
        Point s = new Point();
        dis.getSize(s);
        SpInView = new SpInView(this, s.x, s.y,difficulty);
        setContentView(SpInView);
    }

    protected void onResume(){
        super.onResume();
        SpInView.resume(difficulty);
    }

    protected void onPause(){
        super.onPause();
        SpInView.pause();
    }
}
