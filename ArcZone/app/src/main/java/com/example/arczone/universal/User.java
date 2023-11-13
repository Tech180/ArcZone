package com.example.arczone.universal;

public class User {

    private String user;
    private String pass;
    private String email;
    private Object[][] scores;

    public User(String user, String pass, String email, Object[][] scores){
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.scores = scores;
    }

    public String getUsername(){ return this.user; }

    public String getPassword(){ return this.pass; }

    public String getEmail(){ return this.email; }

    public Object[][] getScores(){ return this.scores; }

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
