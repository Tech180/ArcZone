package com.example.arczone.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class PongGameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private Thread gameThread;
    private final Context context;
    private final Handler handler;
    private final SurfaceHolder surfaceHolder;
    private PongActivity pongActivity;
    volatile boolean playing;
    volatile boolean gameStarted = false;

    // Pong paddle and ball variables
    private float paddleX, paddleY, ballX, ballY, ballSpeedX, ballSpeedY;
    private int paddleWidth, paddleHeight, ballRadius;

    private float opponentPaddleX, opponentPaddleY;
    private int opponentPaddleWidth, opponentPaddleHeight;

    int scoreUser;
    TextView userScore;
    int scoreOpponent;
    TextView opponentScore;
    private int opponentSpeed;

    /**
     * Constructor for PongGameView.
     *
     * @param context The context in which the view is created.
     * @param attrs   The set of attributes associated with the XML layout.
     */
    public PongGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        surfaceHolder = getHolder();

        setGame();

        handler = new Handler(Looper.getMainLooper());

        setFocusable(true);
    }

    /**
     * Initialize paddle and ball positions after the view size is determined.
     *
     * @param w      Current width of the view.
     * @param h      Current height of the view.
     * @param oldW   Old width of the view.
     * @param oldH   Old height of the view.
     */
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        // Initialize paddle and ball positions after the view size is determined
        paddleX = (float) w / 2 - (float) paddleWidth / 2;
        paddleY = h - 2 * paddleHeight;
        ballX = (float) w / 2;
        ballY = (float) h / 2;

        opponentPaddleX = paddleX;
        opponentPaddleY = 0;
    }

    /**
     * Game Loop
     */
    @Override
    public void run() {
        long update = System.currentTimeMillis();
        long updateInterval = 1;

        while (playing) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - update;

            if (elapsedTime >= updateInterval) {
                if (gameStarted) {
                    update();
                    update = currentTime;
                }
            }

            draw();
        }
    }

    /**
     * Update the game state
     */
    private void update() {
        // Update ball position
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Handle collisions with walls on x-axis
        if (ballX - ballRadius < 0 || ballX + ballRadius > getWidth()) {
            ballSpeedX = -ballSpeedX;
        }

        // Handle collisions with walls on y-axis (top and bottom)
        if (ballY - ballRadius < 0 || ballY + ballRadius > getHeight()) {



            // Increment the score when the ball hits the top or bottom edge
            if (ballY - ballRadius < 0) {
                scoreUser++;
                updateScore(userScore, scoreUser, "userScore");
            }
            else {
                scoreOpponent++;
                updateScore(opponentScore, scoreOpponent, "opponentScore");
            }

            System.out.println("User: " + scoreUser + ", Opponent: " + scoreOpponent);


            // Reset position to the center
            ballX = (float) getWidth() / 2;
            ballY = (float) getHeight() / 2;

            if (scoreOpponent >= 10) {
                // End the game and go back to the initial start screen
                playing = false;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pongActivity.gameOver();
                    }
                });
                return;
            }
        }

        // Handle paddle collision
        if (ballY + ballRadius > paddleY && ballX > paddleX && ballX < paddleX + paddleWidth) {
            ballSpeedY = -ballSpeedY;
        }

        /*
            Opponent
        */

        if (opponentPaddleX + (float) opponentPaddleWidth / 2 < ballX) {
            opponentPaddleX += 3;
        }
        else {
            opponentPaddleX -= 3;
        }

        if (opponentPaddleX + (float) opponentPaddleWidth / 2 < ballX) {
            opponentPaddleX += calculatePaddleMovement();
        }
        else {
            opponentPaddleX -= calculatePaddleMovement();
        }

        // Handle opponent paddle collision
        if (ballY - ballRadius < opponentPaddleY + opponentPaddleHeight && ballX > opponentPaddleX && ballX < opponentPaddleX + opponentPaddleWidth) {
            ballSpeedY = -ballSpeedY;
        }
    }

    /**
     * Update the score and display it on the corresponding TextView.
     *
     * @param textView The TextView to update.
     * @param score    The current score.
     * @param player   The player identifier.
     */
    private void updateScore(final TextView textView, final int score, String player) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int newScore = context.getResources().getIdentifier(player, "string", context.getPackageName());
                textView.setText(context.getString(newScore) + score);
            }
        });
    }


    /**
     * Initialize game variables.
     */
    private void setGame() {
        // Initialize game variables
        paddleWidth = 200;
        paddleHeight = 25;
        ballRadius = 25;

        opponentPaddleWidth = paddleWidth;
        opponentPaddleHeight = paddleHeight;

        difficultyChange(10, 10, 1);
    }

    void difficultyChange(int x, int y, int oSpeed) {
        ballSpeedX = x;
        ballSpeedY = y;

        opponentSpeed = oSpeed;
    }

    public void startGame() {

        paddleX = (float) getWidth() / 2 - paddleWidth / 2;
        paddleY = getHeight() - 2 * paddleHeight;
        ballX = (float) getWidth() / 2;
        ballY = (float) getHeight() / 2;

        opponentPaddleX = paddleX;
        opponentPaddleY = 0;

        scoreUser = 0;
        scoreOpponent = 0;

        playing = true;
        gameStarted = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPongActivity(PongActivity activity) {
        this.pongActivity = activity;
    }



    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            // Draw paddle
            Paint paddlePaint = new Paint();
            paddlePaint.setColor(Color.WHITE);
            canvas.drawRect(paddleX, paddleY, paddleX + paddleWidth, paddleY + paddleHeight, paddlePaint);

            // Draw ball
            Paint ballPaint = new Paint();
            ballPaint.setColor(Color.WHITE);
            canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);

            // Draw opponent paddle
            canvas.drawRect(opponentPaddleX, opponentPaddleY, opponentPaddleX + opponentPaddleWidth, opponentPaddleY + opponentPaddleHeight, paddlePaint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private float calculatePaddleMovement() {
        // the ball's position based on its current speed and direction
        float paddleCenter = opponentPaddleX + (float) opponentPaddleWidth / 2;
        float timeToReachPaddle = Math.abs(paddleCenter - ballX) / ballSpeedX;

        // difference between the predicted ball position and the center of the opponent's
        float predictedBallY = ballY + timeToReachPaddle * ballSpeedY;

        if (predictedBallY < opponentPaddleY + (float) opponentPaddleHeight / 2) {
            return -opponentSpeed;
        }
        else {
            return opponentSpeed;
        }
    }

    public void pause() {
        playing = false;

        try {
            gameThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Handle touch events to move the paddle.
     *
     * @param motionEvent The MotionEvent representing the touch event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
            paddleX = motionEvent.getX() - (float) paddleWidth / 2;
        }
        return true;
    }

    /**
     * Handle surface creation event.
     *
     * @param surfaceHolder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        // Initialize game variables and start the game loop when the surface is created
        startGame();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    /**
     * Handle surface destruction event.
     *
     * @param surfaceHolder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        playing = false;

        try {
            gameThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
