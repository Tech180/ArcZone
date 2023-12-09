package com.example.arczone.titlescreen;


import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.example.arczone.R;
import com.example.arczone.universal.universal_methods;
public class TitleScreen extends universal_methods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call universal method for removing title bar
        removeTitleBar(this);
        //set layout
        setContentView(R.layout.activity_title_screen);

        //set views
        ImageView machine = findViewById(R.id.machine);
        ImageView background = findViewById(R.id.background);
        ImageView screen = findViewById(R.id.screen);

        TextView signUp = findViewById(R.id.signUp);
        TextView title = findViewById(R.id.title);

        Button start = findViewById(R.id.start);
        Button guest = findViewById(R.id.guest);
        Button login = findViewById(R.id.login);

        EditText email = findViewById(R.id.fragUsername);
        EditText pass = findViewById(R.id.fragPassword);

        //load in gifs to proper ImageViews
        Glide.with(this).load(R.drawable.background).into(background);
        Glide.with(this).load(R.drawable.machine).into(machine);

        //create new views array for Controller constructor
        // 0:machine, 1:Screen, 2:signUp, 3:start, 4:guest, 5:login, 6:user, 7:pass, 8: title
        View[] views = {machine, screen, signUp, start, guest, login, email, pass, title};

        //create controller constructor --> sets up onClicks and controls animations
        TitleScreenController controller = new TitleScreenController(views, TitleScreen.this);
    }
}