package com.example.arczone.universal;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arczone.R;
import com.example.arczone.leaderboard.gameOver;

import java.util.Objects;

public class universal_methods extends AppCompatActivity{
    protected void removeTitleBar(AppCompatActivity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(activity.getSupportActionBar()).hide();
    }

    /**
     * Used to fade in views
     * @param view view to be faded in
     * @param dur duration of fade in
     */
    protected void fadeIn(View view, int dur){
        view.setVisibility(View.VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(dur);
    }

    /**
     * Used to fade out views
     * @param view view to be faded out
     * @param dur duration of fade out
     */
    protected void fadeOut(View view, int dur){
        view.clearAnimation();
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(dur);
        view.setVisibility(View.INVISIBLE);
    }

    protected void flashAnimation(View view) {
        final AlphaAnimation flashingAnimation = new AlphaAnimation(0.5f, 1.0f);
        flashingAnimation.setDuration(1000); // Adjust the duration as needed
        flashingAnimation.setRepeatMode(Animation.REVERSE);
        flashingAnimation.setRepeatCount(Animation.INFINITE);

        // Start the animation
        view.startAnimation(flashingAnimation);
    }

    public static void gameOver(Context context, String game, Integer score, Class<?> gameClass){
        Intent intent = new Intent(context, gameOver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //pass the game and score
        intent.putExtra("game", game);
        intent.putExtra("score", score);
        intent.putExtra("gameClass", gameClass.getName());
        //start activity
        context.startActivity(intent);


    }
}
