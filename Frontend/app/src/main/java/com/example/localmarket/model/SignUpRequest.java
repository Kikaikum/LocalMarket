package com.example.localmarket.model;

/**
 * Clase que representa la solicitud de registro de un usuario.
 *
 * @author Oriol Estero Sanchez
 */
public class SignUpRequest {
    private double latitud;
    private double longitud;
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private String username;
    private Boolean agricultor;

    /**
     * Constructor de SignUpRequest.
     *
     * @param email    El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @param userName El nombre de usuario del usuario.
     * @param name     El nombre del usuario.
     * @param surname  El apellido del usuario.
     * @param isVendor Un booleano que indica si el usuario es un vendedor.
     */
    public SignUpRequest(String email, String password, String userName, String name, String surname, Boolean isVendor, double latitud, double longitud) {
        this.email = email;
        this.password = password;
        this.nombre = name;
        this.agricultor = isVendor;
        this.username = userName;
        this.apellidos = surname;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public String getSurname() {
        return apellidos;
    }

    public void setSurname(String surname) {
        this.apellidos = surname;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public Boolean getVendor() {
        return agricultor;
    }

    public void setVendor(Boolean vendor) {
        agricultor = vendor;
    }
}
