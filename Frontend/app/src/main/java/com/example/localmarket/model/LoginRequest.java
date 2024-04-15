package com.example.localmarket.model;

/**
 * Clase que representa la solicitud de inicio de sesión.
 * Contiene el nombre de usuario y la contraseña proporcionados por el usuario para iniciar sesión en la aplicación.
 *
 * @author Oriol Estero Sanchez
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructor de la clase LoginRequest.
     *
     * @param username Nombre de usuario proporcionado por el usuario.
     * @param password Contraseña proporcionada por el usuario.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene la contraseña.
     *
     * @return La contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username El nombre de usuario a establecer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Establece la contraseña.
     *
     * @param password La contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
