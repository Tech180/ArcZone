package com.example.arczone.gameselectionscreen;

import static com.example.arczone.universal.universal_methods.removeTitleBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.arczone.R;

public class GameSelectionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeTitleBar(this);

        setContentView(R.layout.activity_game_selection_screen);

        ImageView border = findViewById(R.id.glowborder);
        Glide.with(this).load(R.drawable.glow_border).override(border.getWidth(), border.getHeight()).into(border);

    }
}