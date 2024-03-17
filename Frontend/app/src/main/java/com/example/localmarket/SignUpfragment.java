package com.example.localmarket;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.localmarket.utils.ValidationUtils;

public class SignUpfragment extends Fragment implements View.OnFocusChangeListener {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextName, editTextSurname;
    private Switch aSwitchVendor;
    private Button buttonSignUp;
    private AuthService authService;
    private Context context;

    public SignUpfragment() {
        // Constructor vacío requerido por Fragment
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = AuthService.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        authService = AuthService.getInstance();

        // Buscar referencias de los elementos de la interfaz de usuario
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        aSwitchVendor = view.findViewById(R.id.switchUserType);

        // Establecer el listener de cambio de foco para cada EditText
        editTextUsername.setOnFocusChangeListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
        editTextName.setOnFocusChangeListener(this);
        editTextSurname.setOnFocusChangeListener(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar los campos de entrada
                if (validateFields(getContext())) {
                    // Si los campos son válidos, realizar el registro
                    signUp();
                } else {
                    // Si los campos no son válidos, mostrar un mensaje de error
                    showToast("Por favor, completa correctamente todos los campos.");
                }
            }
        });
        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        Context context = getContext();

        int id = v.getId();
        if (!hasFocus) {
            if (id == R.id.editTextUsername) {
                // Realizar acciones específicas cuando el EditText de username pierde el foco
                // Por ejemplo, validar el nombre de usuario
                String username = editTextUsername.getText().toString().trim();
                editTextUsername.setTextColor(Color.BLACK);
                if (!ValidationUtils.UsernameValidator.isValidUsername(context,username)) {
                    // Si el nombre de usuario no es válido, mostrar un mensaje de error
                    // También puedes cambiar el color de fondo para indicar el error
                    editTextUsername.setTextColor(Color.RED);
                }
            } else if (id == R.id.editTextEmail) {
                // Realizar acciones específicas cuando el EditText de email pierde el foco
                // Por ejemplo, validar el email
                String email = editTextEmail.getText().toString().trim();
                editTextEmail.setTextColor(Color.BLACK);
                if (!ValidationUtils.EmailValidator.isValidEmail(context,email)) {
                    // También puedes cambiar el color de fondo para indicar el error
                    editTextEmail.setTextColor(Color.RED);
                }
            } else if (id == R.id.editTextPassword) {
                // Realizar acciones específicas cuando el EditText de password pierde el foco
                // Por ejemplo, validar la contraseña
                String password = editTextPassword.getText().toString().trim();
                editTextPassword.setTextColor(Color.BLACK);
                if (!ValidationUtils.PasswordValidator.isValidPassword(context,password)) {

                    // También puedes cambiar el color de fondo para indicar el error
                    editTextPassword.setTextColor(Color.RED);
                }
            } else if (id == R.id.editTextName) {
                // Realizar acciones específicas cuando el EditText de name pierde el foco
                // Por ejemplo, validar el nombre
                String name = editTextName.getText().toString().trim();
                editTextName.setTextColor(Color.BLACK);
                if (!ValidationUtils.NameValidator.isValidName(context,name)) {

                    // También puedes cambiar el color de fondo para indicar el error
                    editTextName.setTextColor(Color.RED);
                }
            } else if (id == R.id.editTextSurname) {
                // Realizar acciones específicas cuando el EditText de surname pierde el foco
                // Por ejemplo, validar el apellido
                String surname = editTextSurname.getText().toString().trim();
                editTextSurname.setTextColor(Color.BLACK);;
                if (!ValidationUtils.NameValidator.isValidName(context,surname)) {

                    // También puedes cambiar el color de fondo para indicar el error
                    editTextSurname.setTextColor(Color.RED);
                }
            }
        }
    }


    // Método para validar los campos de entrada
    private boolean validateFields(Context context) {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

        // Verificar la validez de cada campo y retornar false si alguno no es válido
        if (username.isEmpty() || !ValidationUtils.UsernameValidator.isValidUsername(context, username)) {
            return false;
        }

        if (email.isEmpty() || !ValidationUtils.EmailValidator.isValidEmail(context, email)) {
            return false;
        }

        if (password.isEmpty() || !ValidationUtils.PasswordValidator.isValidPassword(context, password)) {
            return false;
        }

        if (name.isEmpty() || !ValidationUtils.NameValidator.isValidName(context, name)) {
            return false;
        }

        if (surname.isEmpty() || !ValidationUtils.NameValidator.isValidName(context, surname)) {
            return false;
        }

        // Todos los campos son válidos
        return true;
    }

    // Método para enviar la información de registro al servidor
    private void signUp() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String isVendor = aSwitchVendor.isChecked()+"";

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
                Log.e("SignUpError", "Error en el registro: " + t.getMessage(), t);
                Toast.makeText(getActivity(), "Error en el registro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
