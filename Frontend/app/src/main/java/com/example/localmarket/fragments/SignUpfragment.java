package com.example.localmarket.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.localmarket.R;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ValidationUtils;
import com.google.android.gms.internal.location.zzz;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/**
 * Fragmento para el registro de nuevos usuarios.
 * Permite a los usuarios completar un formulario para registrarse en la aplicación.
 * Los usuarios deben proporcionar un nombre de usuario, correo electrónico, contraseña, nombre y apellido.
 * También pueden elegir si desean registrarse como vendedores o no.
 *
 * @author Oriol Estero Sanchez
 */
public class SignUpfragment extends Fragment implements View.OnFocusChangeListener {
    private static final int MY_LOCATION_REQUEST_CODE = 101;

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextName, editTextSurname;
    private Switch aSwitchVendor;
    private Button buttonSignUp, buttonCancel;
    private AuthService authService;
    private Context context;
    private FusedLocationProviderClient locationClient;

    public SignUpfragment() {
        // Constructor vacío requerido por Fragment
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = AuthService.getInstance();
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        authService = AuthService.getInstance();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

        // Buscar referencias de los elementos de la interfaz de usuario
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        buttonCancel = view.findViewById(R.id.buttonCancel);
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
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backLogin();
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


    /**
     * Valida los campos del formulario de registro.
     * Comprueba si los campos de nombre de usuario, correo electrónico, contraseña, nombre y apellido son válidos.
     *
     * @param context Contexto de la aplicación.
     * @return true si todos los campos son válidos, false si alguno de ellos no es válido.
     */
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

    /**
     * Envía la información del formulario de registro al servidor.
     * Realiza una llamada al servicio de autenticación para registrar un nuevo usuario.
     * Muestra un mensaje de registro exitoso o maneja cualquier error que ocurra durante el proceso de registro.
     */
    private void signUp() {
        Context context = getContext();
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        Boolean isVendor = aSwitchVendor.isChecked();

        if (context != null && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation().addOnSuccessListener(location -> {
                double latitude;
                double longitude;

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    // No se pudo obtener la ubicación, podrías usar valores por defecto o manejar el error
                    latitude = 0; // Valor por defecto o manejar como error
                    longitude = 0; // Valor por defecto o manejar como error
                    Toast.makeText(getActivity(), "Ubicación no disponible, usando valores por defecto.", Toast.LENGTH_SHORT).show();
                }

                // Proceder con el registro usando la ubicación obtenida o por defecto
                authService.signUpUser(email, password, username, name, surname, isVendor, latitude, longitude, new AuthService.AuthCallback<SignUpResponse>() {
                    @Override
                    public void onSuccess(SignUpResponse response) {
                        // Manejar la respuesta del servidor para el registro exitoso
                        Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Aquí puedes manejar la navegación post-registro, como abrir el fragmento de inicio
                        backLogin();
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Manejar el error de registro
                        Log.e("SignUpError", "Error en el registro: " + t.getMessage(), t);
                        Toast.makeText(getActivity(), "Error en el registro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }).addOnFailureListener(e -> {
                // Falló la obtención de la ubicación
                Log.e("LocationError", "Error al obtener la ubicación", e);
                Toast.makeText(getActivity(), "No se pudo obtener la ubicación: " + e.getMessage(), Toast.LENGTH_LONG).show();
                // Continuar con el registro sin la ubicación o manejar adecuadamente
            });
        } else {
            // Solicitar permisos de ubicación si no están otorgados
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }

                        /**
                         * Muestra un mensaje de Toast en la actividad.
                         *
                         * @param message Mensaje que se mostrará en el Toast.
                         */
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * Regresa al fragmento de inicio de sesión.
     * Reemplaza el fragmento actual con el fragmento de inicio de sesión en el contenedor de fragmentos.
     */
    private void backLogin() {
        FragmentManager fragmentManager = getParentFragmentManager(); // Si estás en un fragmento
        // FragmentManager fragmentManager = requireActivity().getSupportFragmentManager(); // Si estás en una actividad
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

}
