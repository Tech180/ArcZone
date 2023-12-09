package com.example.arczone.leaderboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.example.arczone.R;
import com.google.api.Distribution;


public class Leaderboard extends Fragment {

    private String game;
    private int newScore;

   public Leaderboard(String game, int newScore){
       this.game = game;
       this.newScore = newScore;
   }

   @Override
   public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View view = inflater.inflate(R.layout.fragment_leaderboard, container);

       Glide.with(view).load(R.drawable.glow_border).into((ImageView) getActivity().findViewById(R.id.backgroundImageView));

       new LBController(getActivity(), game, newScore);

       return view;
    }
}