package com.cpre388.Invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.cpre388.arczone.R;

import java.util.Random;

public class Invader {
    RectF rec;
    Random gen = new Random();
    private Bitmap bit1;
    private Bitmap bit2;
    private float l;
    private float h;
    private float x;
    private float y;
    private float Speed;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int Move = RIGHT;
    boolean Visible;

    public Invader(Context context, int row, int col, int Xscreen, int Yscreen){
        rec = new RectF();
        l = Xscreen/20;
        h = Yscreen/20;
        Visible = true;
        int pad = Xscreen/25;
        x = col*(l + pad);
        y = row*(l + pad/4);
        bit1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader1);
        bit2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader2);
        bit1 = Bitmap.createScaledBitmap(bit1,(int)(l),(int)(h),false);
        bit2 = Bitmap.createScaledBitmap(bit2,(int)(l),(int)(h),false);
        Speed = 40;
    }
    public void setInvisible(){
        Visible = false;
    }
    public boolean getVisibility(){
        return Visible;
    }
    public RectF getrec(){
        return rec;
    }
    public Bitmap getBit1(){
        return bit1;
    }
    public Bitmap getBit2(){
        return bit2;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getL(){
        return l;
    }
    public void update(long fps){
        if(Move == LEFT){
            x = x - Speed/fps;
        }
        if(Move == RIGHT){
            x = x + Speed/fps;
        }
        rec.top = y;
        rec.bottom = y + h;
        rec.left = x;
        rec.right = x + l;
    }
    public void dropAndReverse(float difficulty){
        if(Move == LEFT){
            Move = RIGHT;
        }else{
            Move = LEFT;
        }
        y = y + h;
        Speed = Speed * 1.18f * difficulty;
    }
    public boolean Aim(float shipX, float shipL){
        int randNum = -1;
        if((shipX + shipL > x && shipX + shipL < x + l) || (shipX > x && shipX < x + l)){
            randNum = gen.nextInt(250);
            if (randNum == 0){
                return true;
            }
        }
        randNum = gen.nextInt(5000);
        if(randNum == 0){
            return true;
        }
        return false;
    }
}
