package com.example.localmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignUpfragment extends Fragment {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonSignUp;

    public SignUpfragment() {
        // Constructor vacío requerido por Fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        // Buscar referencias de los elementos de la interfaz de usuario
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);

        // Configurar el listener del botón de registro
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar los campos de entrada
                if (validateFields()) {
                    // Enviar la información de registro al servidor
                    signUp();
                } else {
                    // Mostrar un mensaje de error si los campos no son válidos
                    Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // Método para validar los campos de entrada
    private boolean validateFields() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    // Método para enviar la información de registro al servidor
    private void signUp() {
        // Aquí puedes implementar la lógica para enviar la información de registro al servidor
        // Por ejemplo, puedes utilizar Retrofit, Volley o cualquier otra biblioteca para realizar la solicitud HTTP
        // También puedes mostrar un ProgressDialog mientras se envía la solicitud y manejar las respuestas del servidor
        // Aquí se muestra un Toast como ejemplo
        Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
    }
}
