package com.example.localmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.service.AuthService;

public class SignUpfragment extends Fragment {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextName, editTextSurname;
    private Switch aSwitchVendor;
    private Button buttonSignUp;
    private AuthService authService;

    public SignUpfragment() {
        // Constructor vacío requerido por Fragment
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        authService = new AuthService();

        // Buscar referencias de los elementos de la interfaz de usuario
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        aSwitchVendor = view.findViewById(R.id.switchUserType);


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
                    Toast.makeText(getActivity(), R.string.message_empty_fields, Toast.LENGTH_SHORT).show();
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
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() &&!name.isEmpty() && !surname.isEmpty();
    }

    // Método para enviar la información de registro al servidor
    private void signUp() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        Boolean isVendor = aSwitchVendor.isChecked();

        authService.signUpUser(email, password, username, name, surname, isVendor, new AuthService.AuthCallback<SignUpResponse>() {
            @Override
            public void onSuccess(SignUpResponse response) {
                // Manejar la respuesta del servidor para el registro exitoso
                Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                // Aquí puedes manejar la navegación post-registro, como abrir el fragmento de inicio
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error de registro
                Toast.makeText(getActivity(), "Error en el registro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
