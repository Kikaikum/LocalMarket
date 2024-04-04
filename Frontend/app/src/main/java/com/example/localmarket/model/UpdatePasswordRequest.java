package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar la contraseña de un usuario.
 * Autor: Ainhoa
 */
public class UpdatePasswordRequest {
    private String password; // La nueva contraseña del usuario
    private int id; // El identificador único del usuario

    /**
     * Constructor para crear una instancia de UpdatePasswordRequest.
     *
     * @param id       El identificador único del usuario.
     * @param password La nueva contraseña del usuario.
     */
    public UpdatePasswordRequest(int id, String password) {
        this.id = id;
        this.password = password;
    }
}