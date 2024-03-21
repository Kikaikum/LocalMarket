package com.example.localmarket.model;

public class UpdateSurnameRequest {
    private String apellidos;
    private int id;

    // Constructor que acepta neuvos apellidos de usuario
    public UpdateSurnameRequest(int userId, String apellidos) {
        this.id = id;
        this.apellidos = apellidos;
    }


    // Getter y setter para apellidos
    public String getNewSurname() {
        return apellidos;
    }

    public void setNewSurname(String apellidos) {
        this.apellidos=apellidos;
    }
}