package com.example.localmarket.model;

public class UpdatePasswordRequest {
    private String password;
    private String username;

    // Constructor que acepta nueva contraseña
    public UpdatePasswordRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
