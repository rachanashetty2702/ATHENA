package com.example.athena.HelperClasses;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String username;
    private String email;
    private String bday;
    private String weight;
    private String height;

    // Empty constructor for Firebase
    public User() {
    }

    public User(String name, String username, String email, String bday, String height, String weight) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.bday = bday;
        this.height = height;
        this.weight = weight;

    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getBday() {
        return bday;
    }
    public String getWeight() {
        return weight;
    }
    public String getHeight() {
        return height;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("username", username);
        result.put("email", email);
        result.put("bday", bday);
        result.put("height", height);
        result.put("weight", weight);
        return result;
    }
}


