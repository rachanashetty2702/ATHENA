package com.example.athena.HelperClasses;

public class User {
    private String userId;
    private String fullName;
    private String email;
    private String username;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String userId, String fullName, String email, String username) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }

    // Getters and setters
}

