package com.example.arczone.snakegame;

import static com.example.arczone.universal.universal_methods.gameOver;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;

public class SnakeController extends SurfaceView implements Runnable {
    private Thread thread = null;
    private Context context;
    private SoundPool soundPool;
    private int eat_apple = -1;
    private int death = -1;

    public enum Heading{UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;

    private int screenX, screenY;
    private int snakeLength;
    private int appX, appY;
    private int blockSize;

    private final int NUM_BLOCKS_WIDE = 20;
    private int numBlocksHigh;

    private long nextFrameTime;
    private final long FPS = 10;
    private final long MILLIS_PER_SEC = 1000;

    private int score;
    private int[] snakeXs, snakeYs;
    private volatile boolean isPlaying;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean isPostGameOverTap = false;

    public SnakeController(Context context, Point size){
        super(context);

        this.context = context;

        screenX = size.x;
        screenY = size.y;

        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("success.mp3");
            eat_apple = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("death.mp3");
            death = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            // error
        }

        surfaceHolder = getHolder();
        paint = new Paint();

        snakeXs = new int[200];
        snakeYs = new int[200];

        newGame();
    }

    @Override
    public void run() {
        while(isPlaying){
            if(updateRequired()){
                update();
                draw();
            }
        }
    }

    public void pause(){
        isPlaying = false;
        try{
            //TODO: Bring up pause fragment here
            thread.join();
        }catch (InterruptedException e){
            //error
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;

        spawnApple();

        score = 0;

        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnApple(){
        Random random = new Random();
        appX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        appY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    public void eatApple(){
        snakeLength++;
        spawnApple();

        score++;
        soundPool.play(eat_apple, 1, 1, 0, 0, 1);
    }

    public void moveSnake(){
         for(int i = snakeLength; i > 0; i--){
             snakeXs[i] = snakeXs[i - 1];
             snakeYs[i] = snakeYs[i - 1];
         }

         switch (heading){
             case UP:
                 snakeYs[0]--;
                 break;
             case RIGHT:
                 snakeXs[0]++;
                 break;
             case DOWN:
                 snakeYs[0]++;
                 break;
             case LEFT:
                 snakeXs[0]--;
                 break;
         }
    }

    public boolean death(){
        boolean dead = false;

        //hit screen edge
        if(snakeXs[0] == -1) dead = true;
        if(snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if(snakeYs[0] == -1) dead = true;
        if(snakeYs[0] == numBlocksHigh) dead = true;

        //hit self
        for(int i = snakeLength - 1; i > 0; i--){
            if(snakeXs[0] == snakeXs[i] && snakeYs[0] == snakeYs[i]) dead = true;
        }

        return dead;
    }

    public void update(){
        if(snakeXs[0] == appX && snakeYs[0] == appY) eatApple();

        moveSnake();

        if(death()){
            soundPool.play(death, 1, 1, 0, 0, 1);

            gameOver(context, "Snake", score);
        }
    }

    public void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            //make the screen black
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            //make the snake green
            paint.setColor(Color.argb(255, 51, 204, 51));

            //scale the HUD
            paint.setTextSize(90);
            canvas.drawText("Score: " + score, 10, 70, paint);

            //draw the snake one block at a time
            for(int i = 0; i < snakeLength; i++){
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }

            //make the apple red
            paint.setColor(Color.argb(255, 204, 0, 0));

            //draw apple
            canvas.drawRect(appX *  blockSize,
                    (appY * blockSize),
                    (appX * blockSize) + blockSize,
                    (appY * blockSize) + blockSize,
                    paint);

            //unlock the canvas and post graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired(){
        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SEC / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch (heading) {
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }
}
