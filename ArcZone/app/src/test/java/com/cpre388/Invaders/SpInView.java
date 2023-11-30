package com.cpre388.Invaders;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cpre388.Invaders.Brick;
import com.cpre388.Invaders.Bullet;
import com.cpre388.Invaders.Invader;
import com.cpre388.Invaders.PlayersShip;

import java.io.IOException;

public class SpInView extends SurfaceView implements Runnable {
    Context context;
    private Thread gThread = null;
    private SurfaceHolder Holder;
    private volatile boolean play;
    private boolean pause = true;
    private Canvas canvas;
    private Paint paint;
    private long fps;
    private long timeFrame;
    private int Xscreen;
    private int Yscreen;
    private PlayersShip Pship;
    private Bullet B;

    private Bullet[] invBul = new Bullet[200];
    private int nextBul;
    private int maxInBul = 10;
    Invader[] inv = new Invader[60];
    int numInv = 0;
    private Brick[] bricks = new Brick[400];
    private int numBricks;
    private SoundPool soundP;
    private int playerExp = -1;
    private int invaderExp = -1;
    private int shoot = -1;
    private int damageShelt = -1;
    private int uh = -1;
    private int oh = -1;
    int score = 0;
    private int lives = 3;
    private long menaceInterval = 1000;
    private boolean uhOrOh;
    private long lastMenaceTime = System.currentTimeMillis();
    private float difficulty;
    public SpInView(Context context, int X, int Y, float difficulty){
        super(context);
        this.context = context;
        Holder = getHolder();
        paint = new Paint();
        Xscreen = X;
        Yscreen = Y;
        this.difficulty = difficulty;
        soundP = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor desc;
            desc = assetManager.openFd("shoot.ogg");
            shoot = soundP.load(desc, 0);
            desc = assetManager.openFd("invaderexplode.ogg");
            invaderExp = soundP.load(desc,0);
            desc = assetManager.openFd("damageshelter.ogg");
            damageShelt = soundP.load(desc, 0);
            desc = assetManager.openFd("playerexplode.ogg");
            playerExp = soundP.load(desc, 0);
            desc = assetManager.openFd("damageshelter.ogg");
            damageShelt = soundP.load(desc, 0);
            desc = assetManager.openFd("uh.ogg");
            uh = soundP.load(desc, 0);
            desc = assetManager.openFd("oh.ogg");
            oh = soundP.load(desc, 0);
        }catch(IOException e){
            Log.e("error", "sound files failed to load");
        }
        prepareLevel();
    }

    private void prepareLevel() {
        Pship = new PlayersShip(context, Xscreen, Yscreen);
        B = new Bullet(Yscreen);
        menaceInterval = 1000;
        for(int i = 0; i< invBul.length; i++){
            invBul[i] = new Bullet(Yscreen);
        }
        numInv = 0;
        for(int col = 0; col < 6; col++){
            for(int row = 0; row < 5; row++){
                inv[numInv] = new Invader(context, row, col, Xscreen, Yscreen);
                numInv++;
            }
        }
        numBricks = 0;
        for(int snum = 0; snum<4; snum++){
            for(int col = 0; col < 10; col++){
                for(int row = 0; row < 5; row++){
                    bricks[numBricks] = new Brick(row, col, snum, Xscreen, Yscreen);
                    numBricks++;
                }
            }
        }
    }

    public void pause() {
        play = false;
        try{
            gThread.join();
        }catch (InterruptedException e){
            Log.e("error:", "joining thread");
        }
    }

    public void resume() {
        play = true;
        gThread = new Thread(this);
        gThread.start();
    }

    @Override
    public void run() {
        while(play) {
            long strtFrame = System.currentTimeMillis();
            if (!pause) {
                update(difficulty);
            }
            draw();
            timeFrame = System.currentTimeMillis() - strtFrame;
            if (timeFrame >= 1) {
                fps = 1000 / timeFrame;
            }
            if(!pause){
                if((strtFrame - lastMenaceTime) > menaceInterval){
                    if(uhOrOh){
                        soundP.play(uh,1,1,0,0,1);
                    }else{
                        soundP.play(oh,1,1,0,0,1);
                    }
                    lastMenaceTime = System.currentTimeMillis();
                    uhOrOh = !uhOrOh;
                }
            }
        }
    }

    private void draw() {
        if(Holder.getSurface().isValid()){
            canvas = Holder.lockCanvas();
            canvas.drawColor(Color.argb(255,26,128,182));
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawBitmap(Pship.getBitmap(), Pship.getX(), Yscreen-Pship.getH(), paint);
            for(int i = 0; i< numInv; i++){
                if(inv[i].getVisibility()){
                    if(uhOrOh){
                        canvas.drawBitmap(inv[i].getBit1(), inv[i].getX(), inv[i].getY(), paint);
                    }else{
                        canvas.drawBitmap(inv[i].getBit2(), inv[i].getX(), inv[i].getY(), paint);
                    }
                }
            }
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisiblility()) {
                    canvas.drawRect(bricks[i].getRec(), paint);
                }
            }
            if(B.getStatus()){
                canvas.drawRect(B.getRec(), paint);
            }
            for(int i = 0; i < invBul.length; i++){
                if(invBul[i].getStatus()){
                    canvas.drawRect(invBul[i].getRec(), paint);
                }
            }
            paint.setColor((Color.argb(255,249,129,0)));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + " Lives: " + lives, 10,50, paint);
            Holder.unlockCanvasAndPost(canvas);
        }
    }

    private void update(float difficulty) {
        boolean bump = false;
        boolean lost = false;
        Pship.update(fps, Xscreen);
        for(int i = 0; i < numInv; i++){
            if(inv[i].getVisibility()){
                inv[i].update(fps);
                if(inv[i].Aim(Pship.getX(),Pship.getLength())){
                    if(invBul[nextBul].shoot(inv[i].getX() + inv[i].getL()/2, inv[i].getY(), B.DOWN)){
                        nextBul++;
                        if(nextBul == maxInBul){
                            nextBul = 0;
                        }
                    }
                }
                if(inv[i].getX() > Xscreen - inv[i].getL() || inv[i].getX() < 0){
                    bump = true;
                }
            }
        }
        for(int i = 0; i < invBul.length; i++){
            if(invBul[i].getStatus()){
                invBul[i].update(fps);
            }
        }
        if(bump){
            for(int i = 0; i < numInv; i++){
                inv[i].dropAndReverse(difficulty);
                if(inv[i].getY() > Yscreen - Yscreen/10){
                    lost = true;
                }
            }
            menaceInterval = menaceInterval - 80;
        }
        if(lost){
            prepareLevel();
        }
        if(B.getStatus()){
            B.update(fps);
        }
        if(B.getImpactPointY()<0){
            B.setInactive();
        }
        for(int i = 0; i < invBul.length; i++){
            if(invBul[i].getImpactPointY() > Yscreen){
                invBul[i].setInactive();
            }
        }
        if(B.getStatus()){
            for(int i = 0; i < numInv; i++){
                if(inv[i].getVisibility()){
                    if(RectF.intersects(B.getRec(),inv[i].getrec())){
                        inv[i].setInvisible();
                        soundP.play(invaderExp,1,1,0,0,1);
                        B.setInactive();
                        score = score + 10;
                        if(score == numInv*10){
                            pause = true;
                            score = 0;
                            lives = 3;
                            prepareLevel();
                        }
                    }
                }
            }
        }
        for(int i = 0; i < invBul.length; i++){
            if(invBul[i].getStatus()){
                for(int j = 0; j < numBricks; j++){
                    if(bricks[j].getVisiblility()){
                        if(RectF.intersects(invBul[i].getRec(), bricks[j].getRec())){
                            invBul[i].setInactive();
                            bricks[j].setInVisible();
                            soundP.play(damageShelt,1,1,0,0,1);
                        }
                    }
                }
            }
        }
        if(B.getStatus()){
            for(int i = 0; i < numBricks;i++){
                if(bricks[i].getVisiblility()){
                    if(RectF.intersects(B.getRec(),bricks[i].getRec())){
                        B.setInactive();
                        bricks[i].setInVisible();
                        soundP.play(damageShelt,1,1,0,0,1);
                    }
                }
            }
        }
        for(int i = 0; i < invBul.length; i++){
            if(invBul[i].getStatus()){
                if(RectF.intersects(Pship.getRect(), invBul[i].getRec())){
                    invBul[i].setInactive();
                    lives--;
                    soundP.play(playerExp,1,1,0,0,1);
                    if(lives==0){
                        pause = true;
                        lives = 3;
                        score = 0;
                        prepareLevel();
                    }
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                pause = false;
                if(motionEvent.getY() > Yscreen - Yscreen / 8){
                    if(motionEvent.getX() > Xscreen / 2){
                        Pship.setMovementState(Pship.RIGHT);
                    }else{
                        Pship.setMovementState(Pship.LEFT);
                    }
                }
                if(motionEvent.getY() < Yscreen - Yscreen / 8){
                    if(B.shoot(Pship.getX()+Pship.getLength()/2,Yscreen,B.UP)){
                        soundP.play(shoot,1,1,0,0,1);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(motionEvent.getY() > Yscreen - Yscreen / 10){
                    Pship.setMovementState(Pship.STOP);
                }
                break;
        }
        return true;
    }
}
