package com.example.arczone.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.example.arczone.R;

public class PlayersShip {
    RectF rec;
    private Bitmap bit;
    private float l;
    private float h;
    private float x;
    private float y;
    private float speed;
    public final int STOP  = 0;
    public final int RIGHT = 1;
    public final int LEFT = 2;
    private int shipm = STOP;

    public PlayersShip(Context context, int sX, int sY){
        rec = new RectF();
        l = sX/10;
        h = sY/10;
        x = sX/2;
        y = sY -20;
        bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);
        bit = Bitmap.createScaledBitmap(bit, (int)(l),(int)(h), false);
        speed = 350;
    }
    public void update(long fps, float xs){
        if(shipm == LEFT&& x>-25){
            x = x - speed/fps;
        }
        if(shipm == RIGHT&&x<xs/1.10){
            x = x + speed/fps;
        }
        rec.top = y;
        rec.bottom = y + h;
        rec.left = x;
        rec.right = x + l;
    }
    public RectF getRect(){
        return rec;
    }
    public Bitmap getBitmap(){
        return bit;
    }
    public float getX(){
        return x;
    }
    public float getLength(){
        return l;
    }
    public void setMovementState(int state){
        shipm = state;
    }
    public float getH(){
        return h;
    }
}
