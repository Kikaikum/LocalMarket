package com.example.localmarket.model;

public class UpdateEmailRequest {
    private String email;
    private int id;

    // Constructor que acepta neuvos apellidos de usuario
    public UpdateEmailRequest(int id, String email) {
        this.id = id;
        this.email = email;
    }


    // Getter y setter para apellidos
    public String getNewEmail() {
        return email;
    }

    public void setNewSurname(String email) {
        this.email=email;
    }
}

