package com.example.arczone.firebase;

import java.io.Serializable;
import java.util.Map;

public class ArcZoneUser implements Serializable {

    private String user;
    private String pass;
    private String email;
    private Map<String,Integer> scores;

    public ArcZoneUser(String user, String pass, String email, Map<String, Integer> scores){
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.scores = scores;
    }

    public String getUsername(){ return this.user; }

    public String getPassword(){ return this.pass; }

    public String getEmail(){ return this.email; }

    public Map<String, Integer> getScores(){ return this.scores; }

    //TODO: add firebase code to update username
    public boolean updateUsername(String user){
        return false;
    }

    //TODO: add firebase code to update password
    public boolean updatePassword(String pass){
        return false;
    }

    //TODO: add firebase code to updateEmail
    public boolean updateEmail(String email){
        return false;
    }

    //TODO: add firebase code to updateScore(s)
    public boolean updateScores(Object[][] scores){
        return false;
    }
}
