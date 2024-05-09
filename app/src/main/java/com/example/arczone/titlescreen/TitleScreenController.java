package com.example.arczone.titlescreen;

import static com.google.common.io.Resources.getResource;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arczone.R;
import com.example.arczone.firebase.*;
import com.example.arczone.gameselectionscreen.GameSelectionScreen;
import com.example.arczone.universal.LoadingDialogFragment;
import com.example.arczone.universal.SignUpFragment;
import com.example.arczone.universal.universal_methods;


public class TitleScreenController extends universal_methods {

    private View[] views;
    private AppCompatActivity activity;
    private Handler handler = new Handler();

    private ArcZoneDatabase db;
    private ArcZoneAuth auth;
    public TitleScreenController(View[] views, AppCompatActivity activity){
        this.views = views;
        this.activity = activity;

        //set onClick Listeners for each field (visible and invisible)
        start();

        flashAnimation(views[3]);

        login();
        guest();
        signUp();
        buttonSwapOnInput();

        //initialize the database
        db = new ArcZoneDatabase();
        auth = new ArcZoneAuth(activity);


    }

    /**
     * Sets up the onClick for the start button
     * This fades out the start button, brings up the screen image,
     * fades in the user EditText, pass EditText, Guest button,
     * and sign up Text button
     */
    private void start(){
        views[3].setOnClickListener(v -> {
            // fade out start button
            fadeOut(views[3], 1000);

            // animate the screen image to appear
            Animation scale = AnimationUtils.loadAnimation(activity, R.anim.scale_up);
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
        });
    }

    private void login() {
        views[5].setOnClickListener(v -> {
            String credential = ((EditText) views[6]).getText().toString();
            String pass = ((EditText) views[7]).getText().toString();

            //if the credential is not null
            if (credential.contains("@") && pass != null) {

                LoadingDialogFragment loadingDialog = new LoadingDialogFragment();
                auth.loginUser(credential, pass, activity, loadingDialog, db);
                //if the loading frag is hidden and we are still in this activity...
                if (!loadingDialog.isVisible()) {
                    // Clear password
                    ((EditText) views[7]).setText("");
                }
            }
            else {
                if(!credential.contains("@")){
                    ((EditText) views[6]).setText("");

                    Toast.makeText(activity, "Please enter a valid email", Toast.LENGTH_LONG).show();
                }else {
                    // Clear password
                    ((EditText) views[7]).setText("");

                    Toast.makeText(activity, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void guest(){
        views[4].setOnClickListener(v -> {
            ArcZoneUser arcZoneUser = ArcZoneUser.getInstance("Guest", null, null, null);
            Intent intent = new Intent(activity, GameSelectionScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
    }

    private void signUp(){
        views[2].setOnClickListener(v -> showRegisterPopUp());
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
        signup.show(((TitleScreen)activity).getSupportFragmentManager(), "SignUpFragment");
    }
}
