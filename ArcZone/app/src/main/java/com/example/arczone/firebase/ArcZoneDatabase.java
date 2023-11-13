package com.example.arczone.firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArcZoneDatabase {
    private DatabaseReference databaseReference;

    //initialize
    public ArcZoneDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("arczoneItems");
    }

    public void addItem(String name, String description, String password) {
        // Generate a unique key
        String key = databaseReference.push().getKey();

        ArcZoneUser item = new ArcZoneUser(name, description, password);

        databaseReference.child(key).setValue(item);
    }

}
