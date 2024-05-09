package com.example.arczone.spaceinvaders;

import android.graphics.RectF;

public class Bullet {
    private float x;
    private float y;
    private RectF rec;
    public final int UP = 0;
    public final int DOWN = 1;
    int head = -1;
    float speed = 350;
    private int w = 1;
    private int h;
    private boolean Active;

    public Bullet(int screenY){
        h = screenY/20;
        Active = false;
        rec = new RectF();
    }

    public RectF getRec(){
        return rec;
    }

    public boolean getStatus(){
        return Active;
    }

    public void setInactive(){
        Active = false;
    }

    public float getImpactPointY(){
        if(head == DOWN){
            return y + h;
        }else{
            return y;
        }
    }

    public boolean shoot(float startX, float startY, int dir){
        if(!Active){
            x = startX;
            y = startY;
            head = dir;
            Active = true;
            return true;
        }
        return false;
    }

    public void update(long fps){
        if(head == UP){
            y = y - speed/fps;
        }else{
            y = y + speed/fps;
        }
        rec.left = x;
        rec.right = x + w;
        rec.top = y;
        rec.bottom = y + h;
    }
}
