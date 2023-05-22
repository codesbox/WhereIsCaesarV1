package com.example.whereiscaesar.presentation.util;

public class Feedback {
    public String userName;
    public Integer estimation;
    public String feedback;
    public Integer level;

    public Feedback(String userName, Integer estimation, String feedback, Integer level) {
        this.userName = userName;
        this.estimation = estimation;
        this.feedback = feedback;
        this.level = level;
    }
}
