package com.example.arczone.gameselectionscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arczone.R;

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
                "placeholder text"
        );

        return root;
    }
}