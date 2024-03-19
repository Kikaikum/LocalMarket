package com.example.localmarket.model;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
