package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar el nombre de un usuario.
 * @author Ainhoa
 */
public class UpdateNameRequest {
    private String nombre; // El nuevo nombre de usuario
    private int id; // El identificador único del usuario
    private String token; // El token de autenticación del usuario

    /**
     * Constructor para crear una instancia de UpdateNameRequest.
     *
     * @param id     El identificador único del usuario.
     * @param nombre El nuevo nombre de usuario.
     * @param token  El token de autenticación del usuario.
     */
    public UpdateNameRequest(int id, String nombre, String token) {
        this.id = id;
        this.nombre = nombre;
        this.token = token;
    }

    /**
     * Obtiene el nuevo nombre de usuario.
     *
     * @return El nuevo nombre de usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nuevo nombre de usuario.
     *
     * @param nombre El nuevo nombre de usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
