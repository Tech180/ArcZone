package com.google.firebase.arczone.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Toast;

public class ArcZoneAuth {
    private FirebaseAuth mAuth;

    public ArcZoneAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
            }
            else {
                // Registration failed
                //Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
            }
            else {
                // Login failed
                //Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
