package com.example.localmarket.model;

import com.example.localmarket.MainActivity;

public class SessionManager {
    private MainActivity mainActivity;
    private User currentUser;

    public SessionManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public boolean isLoggedIn() {
        // Verifica si el usuario está actualmente autenticado
        // Aquí puedes implementar la lógica según tus necesidades
        return currentUser != null;
    }

    public boolean isAgricultor() {
        // Verifica si el usuario actual es un vendedor
        // Puedes implementar la lógica según tus necesidades
        return currentUser != null && currentUser.getAgricultor();
    }

    public String getAuthToken() {
        // Devuelve el token de autenticación del usuario actual
        // Aquí puedes implementar la lógica para obtener el token de autenticación
        // Puedes obtenerlo de SharedPreferences u otro lugar donde lo hayas almacenado
        return ""; // Cambia esto para devolver el token real
    }

    public User getUser() {
        // Devuelve el usuario de la sesión actual
        return currentUser;
    }

    // Otros métodos para establecer y actualizar el usuario actual, si es necesario
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void clearSession() {
        // Limpia la sesión actual, por ejemplo, cuando el usuario cierra sesión
        currentUser = null;
        // Aquí puedes agregar más lógica para limpiar otros datos de la sesión si es necesario
    }
}
