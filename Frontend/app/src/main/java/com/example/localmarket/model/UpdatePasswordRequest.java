package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar la contraseña de un usuario.
 * Autor: Ainhoa
 */
public class UpdatePasswordRequest {
    private String password; // La nueva contraseña del usuario
    private int id; // El identificador único del usuario
    private String token; // El token de autenticación del usuario

    /**
     * Constructor para crear una instancia de UpdatePasswordRequest.
     *
     * @param id       El identificador único del usuario.
     * @param password La nueva contraseña del usuario.
     * @param token    El token de autenticación del usuario.
     */
    public UpdatePasswordRequest(int id, String password, String token) {
        this.id = id;
        this.password = password;
        this.token = token;
    }

    /**
     * Obtiene la nueva contraseña del usuario.
     *
     * @return La nueva contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la nueva contraseña del usuario.
     *
     * @param password La nueva contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
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
