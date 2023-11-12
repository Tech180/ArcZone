package com.cpre388.arczone;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    public SpInView(Context context, int X, int Y){
        super(context);
        this.context = context;
        Holder = getHolder();
        paint = new Paint();
        Xscreen = X;
        Yscreen = Y;
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
        Pship.update(fps);
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
        long strtFrame = System.currentTimeMillis();
        if(!pause){
            update();
        }
        draw();
        timeFrame = System.currentTimeMillis() - strtFrame;
        if(timeFrame >= 1){
            fps = 1000/timeFrame;
        }
    }

    private void draw() {
        if(Holder.getSurface().isValid()){
            canvas = Holder.lockCanvas();
            canvas.drawColor(Color.argb(255,265,128,182));
            paint.setColor(Color.argb(255,265,128,182));
            canvas.drawBitmap(Pship.getBitmap(), Pship.getX(), Yscreen-50, paint);
            paint.setColor((Color.argb(255,249,129,0)));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + " Lives: " + lives, 10,50, paint);
            Holder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        boolean bump = false;
        boolean lost = false;
        if(lost){
            prepareLevel();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
