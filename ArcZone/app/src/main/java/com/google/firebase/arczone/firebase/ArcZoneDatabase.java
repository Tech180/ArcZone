package com.google.firebase.arczone.firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArcZoneDatabase {
    private DatabaseReference databaseReference;

    //initialize
    public ArcZoneDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("arczoneItems");
    }

    public void addItem(String name, String description, int rating) {
        // Generate a unique key
        String key = databaseReference.push().getKey();

        ArcZoneItem item = new ArcZoneItem(name, description, rating);

        databaseReference.child(key).setValue(item);
    }

}
