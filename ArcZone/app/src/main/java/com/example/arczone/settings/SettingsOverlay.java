package com.example.arczone.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.arczone.R;

public class SettingsOverlay extends Fragment {

    private SeekBar difficultySlider;
    private SeekBar musicEffectsSlider;
    private Button closeButton;
    private ImageView arcadeScreen;
    private ImageView cog;


    public SettingsOverlay() {}

    public SettingsOverlay(Boolean toggle) {

        showCog(toggle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_overlay, container, false);

        difficultySlider = view.findViewById(R.id.difficultyBar);
        musicEffectsSlider = view.findViewById(R.id.musicEffects);
        closeButton = view.findViewById(R.id.closeButton);
        arcadeScreen = view.findViewById(R.id.arcadeScreen);
        cog = view.findViewById(R.id.Cog);

        difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView difficultyLabel = view.findViewById(R.id.difficultyLabel);
                //difficultyLabel.setText(0);
                difficultyLabel.setText(getString(R.string.difficulty, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        musicEffectsSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView musicEffectsLabel = view.findViewById(R.id.musicEffectsLabel);
                musicEffectsLabel.setText(getString(R.string.music_effects, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        cog.setOnClickListener(v -> {
            if (!isAdded()) {
                showOverlay(arcadeScreen);
                fadeIn(view, 500);
            }
        });

        closeButton.setOnClickListener(v -> {
            fadeOut(view, 500);
            hideOverlay();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView arcadeScreen = view.findViewById(R.id.arcadeScreen);
        cog = view.findViewById(R.id.Cog);

        System.out.println("Cog: " + cog);

        showCog(true);
    }

    public void showCog(Boolean toggle) {
        if(toggle) {
            cog.setVisibility(View.VISIBLE);
        }
        else {
            cog.setVisibility(View.INVISIBLE);
        }


    }

    public void showOverlay(ImageView arcadeScreen) {
        Log.e("this", "arcadeScreen: " + arcadeScreen);

        arcadeScreen.setVisibility(View.VISIBLE);
        //difficultySlider.setVisibility(View.VISIBLE);
        //musicEffectsSlider.setVisibility(View.VISIBLE);

    }

    public void hideOverlay() {

        //Cog.setVisibility(View.INVISIBLE);
    }

    /**
     * Used to fade in views
     * @param view view to be faded in
     * @param dur duration of fade in
     */
    private void fadeIn(View view, int dur) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(dur);
        view.startAnimation(fadeIn);
    }

    /**
     * Used to fade out views
     * @param view view to be faded out
     * @param dur duration of fade out
     */
    private void fadeOut(View view, int dur) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(dur);
        view.startAnimation(fadeOut);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
