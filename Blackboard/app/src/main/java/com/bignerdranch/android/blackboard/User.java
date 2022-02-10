package com.bignerdranch.android.blackboard;

public class User {
    private String number;
    private String password;
    private String token;

    public User(String number,String password) {
        this.number = number;
        this.password = password;
    }

    public String getToken() {
        return token;
    }
}
