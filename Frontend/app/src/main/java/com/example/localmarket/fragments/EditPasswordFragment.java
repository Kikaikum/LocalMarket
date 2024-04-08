package com.example.localmarket.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.localmarket.R;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.example.localmarket.utils.ValidationUtils;

/**
 * Fragmento para editar la contraseña del usuario.
 * Autor: Ainoha
 */
public class EditPasswordFragment extends Fragment {

    private EditText newPassword;
    private EditText repeatPassword;
    private Button buttonListo;
    private ValidationUtils.PasswordValidator passwordValidator;
    Context context;
    private TokenManager tokenManager;

    /**
     * Constructor por defecto de la clase EditPasswordFragment.
     */
    public EditPasswordFragment() {
        // Constructor vacío requerido
    }

    /**
     * Método estático para crear una nueva instancia del fragmento EditPasswordFragment.
     *
     * @return Una nueva instancia de EditPasswordFragment.
     */
    public static EditPasswordFragment newInstance() {
        return new EditPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editpassword_fragment, container, false);

        // Inicializar vistas
        newPassword = rootView.findViewById(R.id.newPassword);
        repeatPassword = rootView.findViewById(R.id.repeatPassword);
        buttonListo = rootView.findViewById(R.id.buttonListo);

        // Inicializar el validador de contraseñas
        passwordValidator = new ValidationUtils.PasswordValidator();

        // Inicializar el TokenManager
        tokenManager = new TokenManager(getActivity());

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context=getContext();

                // Obtener las contraseñas ingresadas por el usuario
                String newPwd = newPassword.getText().toString();
                String repeatPwd = repeatPassword.getText().toString();

                // Validar las contraseñas
                validatePasswords(newPwd, repeatPwd);
            }
        });

        return rootView;
    }

    /**
     * Método para validar las contraseñas ingresadas por el usuario.
     *
     * @param newPassword     La nueva contraseña ingresada por el usuario.
     * @param repeatPassword  La repetición de la nueva contraseña ingresada por el usuario.
     */
    private void validatePasswords(String newPassword, String repeatPassword) {
        context=getContext();
        // Verificar si las contraseñas coinciden
        if (newPassword.equals(repeatPassword)) {
            // Verificar si la contraseña cumple con los criterios de validación
            if (passwordValidator.isValidPassword(context, newPassword)) {
                // Contraseña válida, puedes proceder con la actualización
                updatePassword(newPassword);
            } else {
                // Contraseña no válida
                Toast.makeText(getActivity(), "La nueva contraseña no es válida", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Las contraseñas no coinciden
            Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método para actualizar la contraseña del usuario.
     *
     * @param newPassword La nueva contraseña del usuario.
     */
    private void updatePassword(String newPassword) {
        // Obtener el id de usuario del usuario actual
        int id = tokenManager.getUserId();
        String token = tokenManager.getToken(); // Obtener el token de autenticación del usuario

        // Crear una instancia de UpdatePasswordRequest con la nueva contraseña y el token de autenticación
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(id, newPassword, token);

        // Llamar al método updatePassword en AuthService para actualizar la contraseña
        AuthService.getInstance().updatePassword(id, updatePasswordRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Después de actualizar los datos, recarga la actividad
                getActivity().recreate();
                // Volver a la actividad EditProfile
                requireActivity().getSupportFragmentManager().popBackStack();

                // Contraseña actualizada correctamente
                Toast.makeText(getActivity(), "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
                // Error al actualizar la contraseña
                Toast.makeText(getActivity(), "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
