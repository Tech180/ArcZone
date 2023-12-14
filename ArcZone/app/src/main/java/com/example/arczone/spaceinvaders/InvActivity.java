package com.example.arczone.spaceinvaders;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class InvActivity extends AppCompatActivity {

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

    public void onResume(){
        super.onResume();
        SpInView.resume(difficulty);
    }

    public void onPause(){
        super.onPause();
        SpInView.pause();
    }
}
