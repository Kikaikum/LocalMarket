package com.example.localmarket.model;

public class UpdateNameRequest {
    String nombre;
    private int id;

    public UpdateNameRequest(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    // Getter y setter para el nuevo nombre de usuario
    public String getNewNname() {
        return nombre;
    }

    public void setNewName(String nombre) {
        this.nombre = nombre;
    }
}