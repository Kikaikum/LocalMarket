package com.example.localmarket.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.localmarket.activities.ActivitySellerLobby;
import com.example.localmarket.activities.ActivityUserLobby;
import com.example.localmarket.R;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento para el inicio de sesión de usuario.
 * Permite a los usuarios iniciar sesión con su correo electrónico y contraseña.
 * Si el inicio de sesión es exitoso, redirige al usuario a la actividad apropiada según el tipo de usuario.
 * Si el usuario aún no tiene una cuenta, pueden navegar a la pantalla de registro desde aquí.
 *
 * @author Oriol Estero Sanchez
 */
public class LoginFragment extends Fragment {

    private Button loginButton, signUpButton;
    private AuthService authService;
    private TokenManager tokenManager;

    private TextInputEditText emailEditText, passwordEditText;

    public LoginFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = AuthService.getInstance();
        tokenManager = new TokenManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inicializar los widgets
        emailEditText = view.findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = view.findViewById(R.id.editTextTextPassword);
        loginButton = view.findViewById(R.id.buttonLogin);
        signUpButton = view.findViewById(R.id.buttonSignUp);

        // Manejar el evento de clic en el botón de inicio de sesión
        loginButton.setOnClickListener(v -> {
            if (validateFields()) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                iniciarSesion(email, password);
            } else {
                Toast.makeText(getActivity(), R.string.message_empty_fields, Toast.LENGTH_SHORT).show();
            }
        });

        // Manejar el evento de clic en el botón de registro
        signUpButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SignUpfragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }

    /**
     * Valida los campos de correo electrónico y contraseña.
     *
     * @return true si ambos campos no están vacíos, de lo contrario, false.
     */
    private boolean validateFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        return !email.isEmpty() && !password.isEmpty();
    }

    /**
     * Realiza el proceso de inicio de sesión del usuario.
     * Llama al servicio de autenticación para iniciar sesión con las credenciales proporcionadas.
     *
     * @param email    El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    private void iniciarSesion(String email, String password) {
        authService.loginUser(email, password, new AuthService.AuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                String token = response.getToken();
                User user = response.getUser();

                if (user != null) {
                    int userId = user.getId();
                    boolean isVendor = user.getAgricultor();
                    String username= user.getUsername();
                    boolean isAgricultor = user.getAgricultor();

                    // Guardar el token y el ID de usuario en SharedPreferences
                    tokenManager.saveToken(token);
                    tokenManager.saveUserId(userId);
                    tokenManager.saveUsername(username);

                    // Mostrar un mensaje de tostada para indicar el inicio de sesión exitoso
                    Toast.makeText(getActivity(), "Login exitoso", Toast.LENGTH_SHORT).show();

                    // Abrir la pantalla principal
                    openMainScreen(isVendor);
                } else {
                    // El objeto User está nulo, manejar el caso de error aquí
                    Toast.makeText(getActivity(), "Error: Usuario nulo en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(getActivity(), "Error en login: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Abre la pantalla principal de la aplicación según el tipo de usuario.
     *
     * @param vendor true si el usuario es un vendedor, false si es un usuario normal.
     */
    private void openMainScreen(boolean vendor) {
        // Determina la clase de actividad a abrir según el tipo de usuario
        Class<?> activityClass = vendor ? ActivitySellerLobby.class : ActivityUserLobby.class;
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
        requireActivity().finish();
    }
}
