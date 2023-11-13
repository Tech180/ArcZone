package com.example.arczone.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.widget.Toast;

public class ArcZoneAuth {
    private static FirebaseAuth mAuth;
    private static FirebaseUser user = null;
    private static Context context;

    public ArcZoneAuth(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password, String username) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();

                String userId = user.getUid();
                DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");
                ArcZoneUser newUser = new ArcZoneUser(username, email, password);
                usersReference.child(userId).setValue(newUser);

            }
            else {
                // Registration failed
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static FirebaseUser loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user = mAuth.getCurrentUser();
            }
            else {
                // Login failed
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                user = null;
            }
        });
        return user;
    }
}
