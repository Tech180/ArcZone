package com.example.arczone.titlescreen;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arczone.R;
import com.example.arczone.firebase.*;
import com.example.arczone.gameselectionscreen.GameSelectionScreen;
import com.example.arczone.universal.SignUpFragment;


public class TitleScreenController {

    private View[] views;
    private TitleScreen activity;
    private Context context;
    private Handler handler = new Handler();

    private ArcZoneDatabase db;
    public TitleScreenController(View[] views, TitleScreen activity){
        this.views = views;
        this.activity = activity;
        this.context = activity.getApplicationContext();

        //set onClick Listeners for each field (visible and invisible)
        start();
        login();
        guest();
        signUp();
        buttonSwapOnInput();

        //initialize the database
        db = new ArcZoneDatabase();

    }

    /**
     * Used to fade in views
     * @param view view to be faded in
     * @param dur duration of fade in
     */
    private void fadeIn(View view, int dur){
        view.setVisibility(View.VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(dur);
    }

    /**
     * Used to fade out views
     * @param view view to be faded out
     * @param dur duration of fade out
     */
    private void fadeOut(View view, int dur){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(dur);
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets up the onClick for the start button
     * This fades out the start button, brings up the screen image,
     * fades in the user EditText, pass EditText, Guest button,
     * and sign up Text button
     */
    private void start(){
        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fade out start button
                fadeOut(views[3], 1000);

                // animate the screen image to appear
                Animation scale = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.scale_up);
                views[1].setVisibility(View.VISIBLE);
                views[1].startAnimation(scale);

                //hide the start button
                fadeOut(views[3], 500);

                // delay the fade in of the user, pass, guest, and signup views
                // until the screen image animation has finished + 100 ms
                handler.postDelayed(() -> {
                    // fade in user edit text
                    fadeIn(views[6], 1000);
                    // fade in pass edit text
                    fadeIn(views[7], 1000);
                    // fade in the guest sign in button
                    fadeIn(views[4], 1000);
                    // fade in the sign up text
                    fadeIn(views[2], 1000);
                }, 1100);
            }
        });
    }

    private void login(){
        views[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String credential = ((EditText)views[6]).getText().toString();
                String pass = ((EditText)views[7]).getText().toString();

                //get the user data from the db via the credential
                ArcZoneUser arcZoneUser = db.getUserData(credential);

                //if provided credential is not an email, look up email via username
                if(!credential.contains("@")){
                    credential = arcZoneUser.getEmail();
                }

                if (ArcZoneAuth.loginUser(credential, pass)) {
                    Intent intent = new Intent(context, GameSelectionScreen.class);
                    intent.putExtra("user", arcZoneUser);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    //clear password
                    ((EditText) views[7]).setText("");
                    Toast.makeText(activity.getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guest(){
        views[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArcZoneUser arcZoneUser = new ArcZoneUser("Guest", null, null, null);
                Intent intent = new Intent(context, GameSelectionScreen.class);
                intent.putExtra("user", arcZoneUser);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void signUp(){
        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterPopUp();
            }
        });
    }

    private void buttonSwapOnInput(){
        ((EditText)views[6]).addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //hide Guest login and show login button
                if(s.length() > 0) {
                    views[4].setVisibility(View.INVISIBLE);
                    views[5].setVisibility(View.VISIBLE);
                }
                // else if no input, show guest login and hide login button
                else {
                    views[4].setVisibility(View.VISIBLE);
                    views[5].setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { /*never used but must be implemented*/ }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*never used but must be implemented*/ }
        });
    }

    private void showRegisterPopUp(){
        SignUpFragment signup = new SignUpFragment();
        signup.show(activity.getSupportFragmentManager(), "SignUpFragment");
    }
}
