package com.example.arczone.gameselectionscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.arczone.R;
import com.example.arczone.pong.PongActivity;
import com.example.arczone.pong.PongGameView;

public class PongPageFragment extends Fragment {


    public PongPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_pong_page, container, false);

        TextView gameDescription = root.findViewById(R.id.pong_description);
        gameDescription.setText(
                getString(R.string.pong_description_part1) +
                getString(R.string.pong_description_part2)
        );

        Button play = root.findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start snake game
                Intent intent = new Intent(getActivity(), PongActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
}