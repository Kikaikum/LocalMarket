package com.example.localmarket.model;
/**
 * Esta clase representa una solicitud para actualizar el nombre de un usuario.
 * @author Ainhoa
 */
public class UpdateNameRequest {
    String nombre;
    private int id;
    /**
     * Constructor para crear una instancia de UpdateNameRequest.
     *
     * @param id     El identificador único del usuario.
     * @param nombre El nuevo nombre de usuario.
     */
    public UpdateNameRequest(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    // Getter y setter para el nuevo nombre de usuario
    /**
     * Obtiene el nuevo nombre de usuario.
     *
     * @return El nuevo nombre de usuario.
     */
    public String getNewNname() {
        return nombre;
    }

    /**
     * Establece el nuevo nombre de usuario.
     *
     * @param nombre El nuevo nombre de usuario.
     */
    public void setNewName(String nombre) {
        this.nombre = nombre;
    }
}