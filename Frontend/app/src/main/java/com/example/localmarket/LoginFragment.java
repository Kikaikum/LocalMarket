package com.example.localmarket;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.network.service.AuthService;

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private AuthService authService;

    public LoginFragment() {
        // Constructor público vacío requerido
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService();
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

    private boolean validateFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        return !email.isEmpty() && !password.isEmpty();
    }

    private void iniciarSesion(String email, String password) {
        authService.loginUser(email, password, new AuthService.AuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                // Login exitoso, manejar según sea necesario
                Toast.makeText(getActivity(), "Login exitoso", Toast.LENGTH_SHORT).show();
                // Aquí puedes cambiar al fragmento o actividad del home o dashboard
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error
                Toast.makeText(getActivity(), "Error en login: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
