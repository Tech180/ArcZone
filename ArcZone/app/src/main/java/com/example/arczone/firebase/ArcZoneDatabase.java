package com.example.arczone.firebase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.api.*;
import com.google.firebase.firestore.Transaction;

import java.util.Map;

public class ArcZoneDatabase {
    private FirebaseFirestore db;
    private CollectionReference users;
    private CollectionReference scores;
    private boolean success = false;
    private String email = "WAIT";

    //initialize
    public ArcZoneDatabase() {
        db = FirebaseFirestore.getInstance();
        users = db.collection("users");
        scores = db.collection("scores");
    }

    /**
     * Get user data from the database by either username or email
     * @param identifier user provided credential
     * @return
     */
    public void getUserData(String identifier){
        users
                .whereEqualTo("email",  identifier)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot doc = task.getResult().getDocuments().get(0);

                        ArcZoneUser.getInstance(
                                doc.getString("username"),
                                doc.getString("password"),
                                doc.getString("email"),
                                ((ArrayList<Map<String, Integer>>) doc.get("scores")).toArray(new Map[3])
                        );
                    }
                });
    }

    public Map<Integer, String>[] getLeaderboard(String identifier){
        // Use a CountDownLatch to wait for the asynchronous operation to complete
        CountDownLatch latch = new CountDownLatch(1);

        // Reference to the document
        DocumentReference docRef = scores.document(identifier);

        //initialize the array to make the compiler happy
        final Map<Integer, String>[][] leaderboard = new Map[][]{null};

        // Get the document snapshot using asynchronous callback
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        //get the array list from the database, convert to an array of size 10 (# of positions on board)
                        leaderboard[0] = ((ArrayList<Map<String, Integer>>) document.get("scores")).toArray(new Map[10]);
                    } else {
                        // Document does not exist
                        System.out.println("Document not found!");
                    }
                } else {
                    // Handle exceptions
                    System.err.println("Error getting document: " + task.getException().getMessage());
                }

                // Release the latch to signal that the operation is complete
                latch.countDown();
            }
        });

        try {
            // Wait for the operation to complete
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return leaderboard[0];
    }

    public boolean addUser(String user, String email, String password) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("username", user);
        newUser.put("email", email);
        newUser.put("password", password);

        ArrayList<Map<String, Integer>> arr = new ArrayList<>();
        Map<String, Integer> pongMap = new HashMap<>();
        pongMap.put("Pong", 0);
        arr.add(0, pongMap);
        Map<String, Integer> snakeMap = new HashMap<>();
        snakeMap.put("Snake", 0);
        arr.add(1, snakeMap);
        Map<String, Integer> spaceMap = new HashMap<>();
        spaceMap.put("Space Invaders", 0);
        arr.add(2, spaceMap);

        newUser.put("scores", arr);

        users.add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String docID = documentReference.getId();
                        success = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        success = false;
                    }
                });

        return success;
    }

    public void updateLeaderboard(Map<Integer, String>[] arr, String game){
        DocumentReference docRef = scores.document(game);

        docRef.set(Arrays.asList(arr));
    }

    //TODO: This needs to be verified.
    public void updateUserScore(Map<String, Integer> score, ArcZoneUser user){
        users.whereEqualTo("username", user.getUsername())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            db.runTransaction((Transaction.Function<Void>) transaction -> {
                                List<Map<String, Object>> currScores = (List<Map<String, Object>>)documentSnapshot.get("scores");

                                for(Map<String, Object> gameScore : currScores){
                                    String gameName = gameScore.keySet().iterator().next();
                                    if(score.containsKey(gameName)){
                                        gameScore.put(gameName, score.get(gameName));
                                    }
                                }
                                transaction.update(documentSnapshot.getReference(), "scores", currScores);
                                return null;
                            }).addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    Log.d(TAG, "Score updated successfully");
                                }else{
                                    Log.e(TAG, "Error updating score", task1.getException());
                                }
                            });
                        }
                    }
                    else{
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
