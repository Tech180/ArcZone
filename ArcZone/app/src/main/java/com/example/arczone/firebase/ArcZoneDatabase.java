package com.example.arczone.firebase;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ArcZoneDatabase {
    private FirebaseFirestore db;
    private CollectionReference users;
    private CollectionReference scores;
    private boolean success = false;

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
    public ArcZoneUser getUserData(String identifier){

        String mode;

        //if identifier contains @, its an email, look up by email field
        if(identifier.contains("@")) mode = "email";
        //else if no @, its a username, look up by username
        else mode = "username";

        final ArcZoneUser[] arcZoneUser = {null};

        users
                .whereEqualTo(mode,  identifier)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);

                            arcZoneUser[0] = ArcZoneUser.getInstance(
                                    doc.getString("username"),
                                    doc.getString("password"),
                                    doc.getString("email"),
                                    (Map<String, Integer>) doc.get("scores")
                            );
                        }
                    }
                });
        return arcZoneUser[0];
    }

    public boolean addUser(String user, String email, String password) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("username", user);
        newUser.put("email", email);
        newUser.put("password", password);

        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("pong", 0);
        scoreMap.put("snake", 0);
        scoreMap.put("space_invaders", 0);

        newUser.put("scores", scoreMap);

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

}
