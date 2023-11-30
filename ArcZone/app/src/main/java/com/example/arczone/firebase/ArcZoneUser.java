package com.example.arczone.firebase;

import java.util.Map;

public class ArcZoneUser {

    private static ArcZoneUser instance;

    private String user;
    private String pass;
    private String email;
    private Map<String, Integer> scores;

    // Private constructor to prevent instantiation
    private ArcZoneUser(String user, String pass, String email, Map<String, Integer> scores) {
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.scores = scores;
    }

    // Method to get the singleton instance
    public static synchronized ArcZoneUser getInstance(String user, String pass, String email, Map<String, Integer> scores) {
        if (instance == null) {
            instance = new ArcZoneUser(user, pass, email, scores);
        }
        return instance;
    }

    // Overloaded method for subsequent calls without providing initial details
    public static synchronized ArcZoneUser getInstance() {
        if (instance == null) {
            // Log an error or throw an exception, as this should not happen
            throw new IllegalStateException("getInstance called before getInstance with initial details.");
        }
        return instance;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUsername(){
        return this.user;
    }

    public Map<String, Integer> getScores(){
        return this.scores;
    }

    //TODO: add firebase code to update username
    public boolean updateUsername(String user) {
        // TODO: Implement the updateUsername method
        return false;
    }

    //TODO: add firebase code to update password
    public boolean updatePassword(String pass) {
        // TODO: Implement the updatePassword method
        return false;
    }

    //TODO: add firebase code to update email
    public boolean updateEmail(String email) {
        // TODO: Implement the updateEmail method
        return false;
    }

    //TODO: add firebase code to update scores
    public boolean updateScores(Object[][] scores) {
        // TODO: Implement the updateScores method
        return false;
    }
}