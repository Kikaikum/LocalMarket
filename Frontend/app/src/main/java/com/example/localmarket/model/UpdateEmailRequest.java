package com.example.localmarket.model;

/**
 * Esta clase representa una solicitud para actualizar el correo electrónico de un usuario.
 * Además, incluye el token de autenticación necesario para la solicitud.
 * @author Ainoha
 */
public class UpdateEmailRequest {
    private String email;
    private int id;
    private String token;

    /**
     * Constructor para crear una instancia de UpdateEmailRequest.
     *
     * @param id    El identificador único del usuario.
     * @param email El nuevo correo electrónico del usuario.
     * @param token El token de autenticación del usuario.
     */
    public UpdateEmailRequest(int id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    /**
     * Obtiene el nuevo correo electrónico del usuario.
     *
     * @return El nuevo correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el nuevo correo electrónico del usuario.
     *
     * @param email El nuevo correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
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
