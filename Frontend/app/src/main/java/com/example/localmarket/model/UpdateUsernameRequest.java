package com.example.localmarket.model;

import com.google.gson.annotations.SerializedName;
public class UpdateUsernameRequest {
    private String username;
    private int userId;

    // Constructor que acepta el nuevo nombre de usuario
    public UpdateUsernameRequest(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // Getter y setter para el nuevo nombre de usuario
    public String getNewUsername() {
        return username;
    }

    public void setNewUsername(String username) {
        this.username = username;
    }
}