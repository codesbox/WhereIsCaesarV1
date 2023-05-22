package com.example.whereiscaesar.presentation.util;

public class User {
    public String firstName;
    public String lastName;
    public String id;
    public Integer feedbackCount;

    public User(String firstName, String lastName, String id, Integer feedbackCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.feedbackCount = feedbackCount;
    }
}
