package com.example.arczone.universal;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arczone.firebase.*;

import com.example.arczone.R;

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
                || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")
                    || username.getText().toString().length() > 10) {

            Boolean field_req = false;

            //check if username is filled out and less than 10 characters
            if(username.getText().toString().equals("") || username.getText().toString().length() > 10) {
                //set invalid border
                username.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                //check if username is empty
                if(username.getText().toString().equals("") || username.getText().toString().isEmpty() == true) field_req = true;
                //check if username is greater than 10 characters
                if(username.getText().toString().length() > 10) makeToast("Username must be 10 characters or less");
            }else{
                username.setBackground(getResources().getDrawable(R.drawable.validtextboxborder));
            }

            //check if email is empty
            if (email.getText().toString().equals("")) {
                email.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder)); // Set text color directly
                field_req = true;
            }else{
                email.setBackground(getResources().getDrawable(R.drawable.validtextboxborder));
            }

            if(password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")){
                if(password.getText().toString().equals("")){
                    password.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                    field_req = true;
                }
                if(confirmPassword.getText().toString().equals("")){
                    confirmPassword.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                    field_req = true;
                }
            } //check if the password and confirm passwords match
            else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                makeToast("Passwords do not match");
                password.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
                confirmPassword.setBackground(getResources().getDrawable(R.drawable.invalidtextboxborder));
            }
            else{
                password.setBackground(getResources().getDrawable(R.drawable.validtextboxborder));
                confirmPassword.setBackground(getResources().getDrawable(R.drawable.validtextboxborder));
            }

            if(field_req) makeToast("Fields Required");

            return false;
        }
        return true;
    }

    public void makeToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}