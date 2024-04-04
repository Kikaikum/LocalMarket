package com.example.localmarket.model;
/**
 * Esta clase representa una solicitud para actualizar el correo electrónico de un usuario.
 * @author Ainoha
 */
public class UpdateEmailRequest {
    private String email;
    private int id;
    /**
     * Constructor para crear una instancia de UpdateEmailRequest.
     *
     * @param id    El identificador único del usuario.
     * @param email El nuevo correo electrónico del usuario.
     */
    // Constructor que acepta neuvos apellidos de usuario
    public UpdateEmailRequest(int id, String email) {
        this.id = id;
        this.email = email;
    }


    // Getter y setter para apellidos
    /**
     * Obtiene el nuevo correo electrónico del usuario.
     *
     * @return El nuevo correo electrónico del usuario.
     */
    public String getNewEmail() {
        return email;
    }
    /**
     * Establece el nuevo correo electrónico del usuario.
     *
     * @param email El nuevo correo electrónico del usuario.
     */
    public void setNewSurname(String email) {
        this.email=email;
    }
}

