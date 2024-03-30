package com.example.localmarket;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.example.localmarket.utils.ValidationUtils;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ValidationUtils;
public class EditPasswordFragment extends Fragment {

    private EditText newPassword;
    private EditText repeatPassword;
    private Button buttonListo;
    private ValidationUtils.PasswordValidator passwordValidator;
    private Context context;
    private TokenManager tokenManager;

    public EditPasswordFragment() {
        // Constructor vacío requerido
    }

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

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener las contraseñas ingresadas por el usuario
                String newPwd = newPassword.getText().toString();
                String repeatPwd = repeatPassword.getText().toString();

                // Validar las contraseñas
                validatePasswords(newPwd, repeatPwd);
            }
        });

        return rootView;
    }

    private void validatePasswords(String newPassword, String repeatPassword) {
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

    private void updatePassword(String newPassword) {
        // Obtener el nombre de usuario del usuario actual
        String username = TokenManager.getInstance(getActivity()).getUserUsername();


        // Crear una instancia de UpdatePasswordRequest con la nueva contraseña
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(username,newPassword);

        // Llamar al método updatePassword en AuthService para actualizar la contraseña
        AuthService.getInstance().updatePassword(username, updatePasswordRequest, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
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
