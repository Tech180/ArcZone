package com.google.firebase.arczone.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cpre388.arczone.R;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test); // Set the layout for this activity

        // Add code to handle UI elements and interactions
        // For example, you can handle button clicks or other user interactions here.

        // Call the sample method to add sample items to the database
    }

    public void sample() {
        ArcZoneDatabase database = new ArcZoneDatabase();

        // Assuming ArcZoneDatabase is correctly set up to add items to the database
        // You are adding sample items to the database here
        database.addItem("Sample Item 1", "This is the first sample item.", "woot");
        database.addItem("Sample Item 2", "This is the second sample item.", "woot");
    }

}
