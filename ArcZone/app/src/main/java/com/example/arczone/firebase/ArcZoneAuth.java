package com.example.arczone.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class ArcZoneAuth {
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase db;
    private static FirebaseUser user = null;
    private static boolean success = false;
    private Context context;

    public ArcZoneAuth(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    public void registerUser(String email, String password, String username) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //attempt to add user to database
                ArcZoneDatabase db = new ArcZoneDatabase();
                db.addUser(username, email, password);

                Toast.makeText(context, "Registration Successful!", Toast.LENGTH_LONG).show();
            }
            //if unsuccessful, tell user why (like email already used).
            else {
                String error = "Registration failed.";

                if(task.getException() != null){
                    error = task.getException().getMessage();
                }

                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                success = true;
            }
            else {
                // Login failed
                //Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                success = false;
            }
        });
        return success;
    }
}
