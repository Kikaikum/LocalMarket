package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar el nombre de usuario.
 * Autor: Ainhoa
 */
public class UpdateUsernameRequest {
    private String username; // El nuevo nombre de usuario

    private int id; // El identificador único del usuario

    /**
     * Constructor para crear una instancia de UpdateUsernameRequest.
     *
     * @param id   El identificador único del usuario.
     * @param username El nuevo nombre de usuario.
     */
    public UpdateUsernameRequest(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Obtiene el nuevo nombre de usuario.
     *
     * @return El nuevo nombre de usuario.
     */
    public String getNewUsername() {
        return username;
    }

    /**
     * Establece el nuevo nombre de usuario.
     *
     * @param username El nuevo nombre de usuario.
     */
    public void setNewUsername(String username) {
        this.username = username;
    }
}
