package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar los apellidos de un usuario.
 * @author : Ainoha
 */
public class UpdateSurnameRequest {
    private String apellidos; // Los nuevos apellidos del usuario
    private int id; // El identificador único del usuario
    private String token; // El token de autenticación del usuario

    /**
     * Constructor para crear una instancia de UpdateSurnameRequest.
     *
     * @param id        El identificador único del usuario.
     * @param apellidos Los nuevos apellidos del usuario.
     * @param token     El token de autenticación del usuario.
     */
    public UpdateSurnameRequest(int id, String apellidos, String token) {
        this.id = id;
        this.apellidos = apellidos;
        this.token = token;
    }

    /**
     * Obtiene los nuevos apellidos del usuario.
     *
     * @return Los nuevos apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los nuevos apellidos del usuario.
     *
     * @param apellidos Los nuevos apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
