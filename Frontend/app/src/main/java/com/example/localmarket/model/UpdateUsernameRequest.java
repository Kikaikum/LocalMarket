package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar el nombre de usuario.
 * Autor: Ainhoa
 */
public class UpdateUsernameRequest {
    private String username; // El nuevo nombre de usuario
    private int id; // El identificador único del usuario
    private String token; // El token de autenticación del usuario

    /**
     * Constructor para crear una instancia de UpdateUsernameRequest.
     *
     * @param id       El identificador único del usuario.
     * @param username El nuevo nombre de usuario.
     * @param token    El token de autenticación del usuario.
     */
    public UpdateUsernameRequest(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    /**
     * Obtiene el nuevo nombre de usuario.
     *
     * @return El nuevo nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nuevo nombre de usuario.
     *
     * @param username El nuevo nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El identificador único del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param id El identificador único del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el token de autenticación del usuario.
     *
     * @return El token de autenticación del usuario.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token de autenticación del usuario.
     *
     * @param token El token de autenticación del usuario.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
