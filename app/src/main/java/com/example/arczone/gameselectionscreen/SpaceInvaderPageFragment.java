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
import com.example.arczone.snakegame.Snake;
import com.example.arczone.spaceinvaders.InvActivity;

public class SpaceInvaderPageFragment extends Fragment {



    public SpaceInvaderPageFragment() {
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
        View root = inflater.inflate(R.layout.fragment_space_invader_page, container, false);

        TextView gameDescription = root.findViewById(R.id.si_description);
        gameDescription.setText(
                "Destroy all of the invaders before they reach the bottom or you lose all your lives!"
        );

        Button play = root.findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start snake game
                Intent intent = new Intent(getActivity(), InvActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
}