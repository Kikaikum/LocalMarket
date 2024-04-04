package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar los apellidos de un usuario.
 * @author : Ainoha
 */
public class UpdateSurnameRequest {
    private String apellidos; // Los nuevos apellidos del usuario
    private int id; // El identificador único del usuario

    /**
     * Constructor para crear una instancia de UpdateSurnameRequest.
     *
     * @param id    El identificador único del usuario.
     * @param apellidos Los nuevos apellidos del usuario.
     */
    public UpdateSurnameRequest(int id, String apellidos) {
        this.id = id;
        this.apellidos = apellidos;
    }

    /**
     * Obtiene los nuevos apellidos del usuario.
     *
     * @return Los nuevos apellidos del usuario.
     */
    public String getNewSurname() {
        return apellidos;
    }

    /**
     * Establece los nuevos apellidos del usuario.
     *
     * @param apellidos Los nuevos apellidos del usuario.
     */
    public void setNewSurname(String apellidos) {
        this.apellidos = apellidos;
    }
}
