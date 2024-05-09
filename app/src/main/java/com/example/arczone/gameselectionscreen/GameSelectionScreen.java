package com.example.arczone.gameselectionscreen;

import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.arczone.R;
import com.example.arczone.firebase.ArcZoneUser;
import com.example.arczone.gameselectionscreen.GSSController.*;
import com.example.arczone.universal.universal_methods;

public class GameSelectionScreen extends universal_methods {

    private ViewPager2 viewPager;
    private CarouselPagerAdapter carouselPagerAdapter;

    private ArcZoneUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeTitleBar(this);

        //receive
        user = (ArcZoneUser) getIntent().getSerializableExtra("user");

        setContentView(R.layout.activity_game_selection_screen);

        ImageView border = findViewById(R.id.glowborder);
        Glide.with(this).load(R.drawable.glow_border).override(border.getWidth(), border.getHeight()).into(border);

        // get viewPager and set adapter
        viewPager = findViewById(R.id.viewPager);
        carouselPagerAdapter = new GSSController.CarouselPagerAdapter(this);
        viewPager.setAdapter(carouselPagerAdapter);

        // set page transformer (depth animation)
        viewPager.setPageTransformer(new GSSController.DepthPageTransformer());

    }
}
