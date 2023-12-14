package com.example.arczone.universal;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.arczone.R;

import com.example.arczone.pong.PongActivity;
import com.example.arczone.snakegame.Snake;
import com.example.arczone.spaceinvaders.InvActivity;

public class SettingsOverlay extends Fragment{

    private SeekBar difficultySlider;
    private TextView difficultyLabel;
    private SeekBar musicEffectsSlider;
    private TextView musicEffectsLabel;
    private AppCompatButton closeButton;
    private ImageView arcadeScreen;
    private ImageView cog;
    private SharedPreferences sharedPreferences;


    public SettingsOverlay() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.settings_overlay, container, false);

        difficultySlider = view.findViewById(R.id.difficultyBar);
        difficultyLabel = view.findViewById(R.id.difficultyLabel);
        musicEffectsSlider = view.findViewById(R.id.musicEffects);
        musicEffectsLabel = view.findViewById(R.id.musicEffectsLabel);
        closeButton = view.findViewById(R.id.closeButton);
        arcadeScreen = view.findViewById(R.id.arcadeScreen);
        cog = view.findViewById(R.id.Cog);

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        int savedDifficulty = sharedPreferences.getInt("difficulty", 0);
        difficultySlider.setProgress(savedDifficulty);
        difficultyLabel.setText(getString(R.string.difficulty, savedDifficulty));

        int savedMusic = sharedPreferences.getInt("music_effects", 0);
        musicEffectsSlider.setProgress(savedMusic);
        musicEffectsLabel.setText(getString(R.string.music_effects, savedMusic));

        int temp = 0;

        difficultyLabel.setText(getString(R.string.difficulty, difficultySlider.getProgress()));
        musicEffectsLabel.setText(getString(R.string.music_effects, musicEffectsSlider.getProgress()));

        AudioManager audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);

        difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                difficultyLabel.setText(getString(R.string.difficulty, progress));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("difficulty", progress);
                editor.apply();

                switch(progress) {
                    case 0:
                        ((SettingsInterface) requireActivity()).setDifficulty(1);
                        System.out.println("here...");
                        break;
                    case 1:
                        ((SettingsInterface) requireActivity()).setDifficulty(2);
                        System.out.println("here2...");
                        break;
                    case 2:
                        ((SettingsInterface) requireActivity()).setDifficulty(3);
                        System.out.println("here3...");
                        break;
                    default:
                        break;
                }


                difficultyLabel.setText(getString(R.string.difficulty, progress));

                System.out.println("labelllll: " + difficultyLabel.getText());
                System.out.println("progress: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        musicEffectsSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                musicEffectsLabel.setText(getString(R.string.music_effects, progress));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("music_effects", progress);
                editor.apply();


                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int newVolume = (int) ((progress / 100.0) * maxVolume);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, AudioManager.FLAG_SHOW_UI);


                musicEffectsLabel.setText(getString(R.string.music_effects, progress));

                //((SettingsInterface) requireActivity()).setMusicEffects(progress);

                System.out.println("labelllll music: " + musicEffectsLabel.getText());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        showCog(cog);

        cog.setOnClickListener(v -> {
            showOverlay();
            hideCog(cog);
            fadeElementsIn(250);
            gamePause();

        });

        closeButton.setOnClickListener(v -> {
            fadeElementsOut(250);
            showCog(cog);
            hideOverlay();
            gameResume();
        });

        return view;
    }


    public void showCog(ImageView cog) {
            cog.setVisibility(View.VISIBLE);
    }

    public void hideCog(ImageView cog) {
        cog.setVisibility(View.INVISIBLE);
    }

    public void fadeElementsIn(int dur) {
        fadeIn(arcadeScreen, dur);
        fadeIn(musicEffectsLabel, dur);
        fadeIn(difficultyLabel, dur);
        fadeIn(musicEffectsSlider, dur);
        fadeIn(difficultySlider, dur);
        fadeIn(closeButton, dur);
    }

    public void fadeElementsOut(int dur) {
        fadeOut(arcadeScreen, dur);
        fadeOut(musicEffectsLabel, dur);
        fadeOut(difficultyLabel, dur);
        fadeOut(musicEffectsSlider, dur);
        fadeOut(difficultySlider, dur);
        fadeOut(closeButton, dur);
    }

    public void showOverlay() {
        arcadeScreen.setVisibility(View.VISIBLE);
        difficultySlider.setVisibility(View.VISIBLE);
        musicEffectsSlider.setVisibility(View.VISIBLE);
        difficultyLabel.setVisibility(View.VISIBLE);
        musicEffectsLabel.setVisibility(View.VISIBLE);
        closeButton.setVisibility(View.VISIBLE);
    }

    public void hideOverlay() {
        arcadeScreen.setVisibility(View.INVISIBLE);
        difficultySlider.setVisibility(View.INVISIBLE);
        musicEffectsSlider.setVisibility(View.INVISIBLE);
        difficultyLabel.setVisibility(View.INVISIBLE);
        musicEffectsLabel.setVisibility(View.INVISIBLE);
        closeButton.setVisibility(View.INVISIBLE);
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


    public void gamePause() {

        if (getActivity() instanceof PongActivity) {
            ((PongActivity) getActivity()).onPause();
        }
        if (getActivity() instanceof Snake) {
            ((Snake) getActivity()).onPause();
        }

        if (getActivity() instanceof InvActivity) {
            ((InvActivity) getActivity()).onPause();
        }


    }

    public void gameResume() {

        if (getActivity() instanceof PongActivity) {
            ((PongActivity) getActivity()).onResume();
        }
        if (getActivity() instanceof Snake) {
            ((Snake) getActivity()).onResume();
        }

        if (getActivity() instanceof InvActivity) {
            ((InvActivity) getActivity()).onResume();
        }

    }


}
