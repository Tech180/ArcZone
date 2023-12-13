package com.example.arczone.firebase;

import com.example.arczone.gameselectionscreen.GameSelectionScreen;
import com.example.arczone.universal.LoadingDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

    public void loginUser(String email, String password, AppCompatActivity activity, LoadingDialogFragment loadingDialog, ArcZoneDatabase db) {
        loadingDialog.show(activity.getSupportFragmentManager(), "loadingDialog");

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //hide the loading frag
                loadingDialog.dismiss();
                //create new ArcZoneUser singleton
                db.getUserData(email);
                //start game selection screen activity
                Intent intent = new Intent(activity, GameSelectionScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
            else {
                loadingDialog.dismiss();
                Toast.makeText(activity, "Incorrect Credentials", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUserAndShowLoadingDialog(String email, String password, FragmentManager mgr) {
        // Show the loading dialog
        LoadingDialogFragment loadingDialog = new LoadingDialogFragment();
        loadingDialog.show(mgr, "loadingDialog");
    }
}
