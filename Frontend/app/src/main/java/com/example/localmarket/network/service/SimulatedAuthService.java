package com.example.localmarket.network.service;

import com.example.localmarket.model.User;

import java.util.HashMap;
import java.util.Map;

public class SimulatedAuthService {

    private static SimulatedAuthService instance;
    private User simulatedUser;



        private Map<String, User> userData; // Almacenamiento simulado de datos de usuario

    private SimulatedAuthService() {
            userData = new HashMap<>();
        // Generar datos de usuario simulados
        simulatedUser = new User("John", "John Doe", "johndoe123", "John@gmail.com", "Doe1-ttttt");
        userData.put(simulatedUser.getUsername(), simulatedUser);
    }

    public static SimulatedAuthService getInstance() {
        if (instance == null) {
            instance = new SimulatedAuthService();
        }
        return instance;
    }

    public void getUserProfile(AuthService.ProfileCallback callback) {
        // Devolver el usuario simulado cuando se solicite el perfil del usuario
        callback.onSuccess(simulatedUser);
    }

    public void deleteAccount( AuthService.AuthCallback<Void> callback) {
        callback.onSuccess(null);
    }

    public void updateName(String newName) {
        // Actualizar el nombre del usuario simulado
        simulatedUser.setName(newName);
        // Actualizar los datos del usuario en el almacenamiento simulado
        userData.put(simulatedUser.getUsername(), simulatedUser);
    }

    // Métodos adicionales simulados según sea necesario para otras funcionalidades de AuthService
}