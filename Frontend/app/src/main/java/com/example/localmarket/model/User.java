package com.example.localmarket.model;
/**
 * Esta clase representa un usuario en el sistema.
 */


public class User {

    private String nombre;
    private String apellidos;
    private String username;
    private String email;
    private String password;
    private int id;
    private boolean agricultor;
    /**
     * Constructor para crear una instancia de User.
     * @author Oriol + Ainoha
     * @param nombre     El nombre del usuario.
     * @param apellidos  Los apellidos del usuario.
     * @param username   El nombre de usuario del usuario.
     * @param email      El correo electrónico del usuario.
     * @param password   La contraseña del usuario.
     * @param id         El identificador único del usuario.
     * @param agricultor Indica si el usuario es un agricultor o no.
     */
    // Constructor
    public User(String nombre, String apellidos, String username, String email, String password, int id, boolean agricultor) {
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.agricultor= agricultor;
    }

    // Getters y setters

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getName() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setName(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     *
     * @return Los apellidos del usuario.
     */
    public String getSurname() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     *
     * @param apellidos Los apellidos del usuario.
     */
    public void setSurname(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario del usuario.
     *
     * @param username El nombre de usuario del usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña del usuario.
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
     * Obtiene si el usuario es un agricultor o no.
     *
     * @return true si el usuario es un agricultor, false si no lo es.
     */
    public boolean getAgricultor() {
        return agricultor;
    }

    /**
     * Establece si el usuario es un agricultor o no.
     *
     * @param agricultor true si el usuario es un agricultor, false si no lo es.
     */
    public void setAgricultor(boolean agricultor) {
        this.agricultor = agricultor;
    }
}
