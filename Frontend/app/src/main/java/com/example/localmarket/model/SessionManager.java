package com.example.localmarket.model;

import com.example.localmarket.activities.MainActivity;

/**
 * Clase que gestiona la sesión del usuario.
 *
 * @author Oriol Estero Sanchez
 */
public class SessionManager {
    private MainActivity mainActivity;
    private User currentUser;

    /**
     * Constructor de la clase SessionManager.
     *
     * @param mainActivity La actividad principal de la aplicación.
     */
    public SessionManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Verifica si el usuario ha iniciado sesión.
     *
     * @return true si el usuario ha iniciado sesión, false de lo contrario.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Verifica si el usuario actual es un vendedor.
     *
     * @return true si el usuario es un vendedor, false de lo contrario.
     */
    public boolean isAgricultor() {
        return currentUser != null && currentUser.getAgricultor();
    }

    /**
     * Obtiene el token de autenticación del usuario.
     *
     * @return El token de autenticación del usuario actual.
     */
    public String getAuthToken() {
        return ""; // Cambia esto para devolver el token real
    }

    /**
     * Obtiene el usuario de la sesión actual.
     *
     * @return El usuario de la sesión actual.
     */
    public User getUser() {
        return currentUser;
    }

    /**
     * Establece el usuario actual.
     *
     * @param user El usuario a establecer como actual.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Limpia la sesión actual.
     * Se llama, por ejemplo, cuando el usuario cierra sesión.
     */
    public void clearSession() {
        currentUser = null;
        // Aquí puedes agregar más lógica para limpiar otros datos de la sesión si es necesario
    }
}
