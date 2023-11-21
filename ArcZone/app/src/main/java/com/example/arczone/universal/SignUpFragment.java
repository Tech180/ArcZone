package com.example.arczone.universal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arczone.firebase.*;

import com.example.arczone.R;
import com.google.common.eventbus.AllowConcurrentEvents;

public class SignUpFragment extends DialogFragment {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUp;

    public SignUpFragment() { /* Required empty public constructor*/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.signup);

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        username = view.findViewById(R.id.fragUsername);
        email = view.findViewById(R.id.fragEmail);
        password = view.findViewById(R.id.fragPassword);
        confirmPassword = view.findViewById(R.id.fragConfirmPassword);
        signUp = view.findViewById(R.id.fragSignUpButton);

        signUp.setOnClickListener(v -> {
            if(verifyFields()) {
                ArcZoneAuth auth = new ArcZoneAuth(getContext());
                //attempt to register user login
                auth.registerUser(email.getText().toString(), password.getText().toString(), username.getText().toString());
                // clear the fragment from the screen
                this.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        if(getDialog() == null) return;

        int width = 1000;
        int height = 1250;

        getDialog().getWindow().setLayout(width, height);
    }

    public Boolean verifyFields() {
        if (username.getText().toString().equals("") || email.getText().toString().equals("")
                || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")) {
            if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                password.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                confirmPassword.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
            } else {
                Toast.makeText(getActivity(), "Fields required", Toast.LENGTH_SHORT).show();
                if (username.getText().toString().equals("")) {
                    username.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                }
                if (email.getText().toString().equals("")) {
                    email.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder)); // Set text color directly
                }
                if (password.getText().toString().equals("")) {
                    password.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder)); // Set text color directly
                }
                if (confirmPassword.getText().toString().equals("")) {
                    confirmPassword.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder)); // Set text color directly
                }
            }
            return false;
        }
        return true;
    }
}