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
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ValidationUtils;

public class EditPasswordFragment extends Fragment {

    private EditText actualPassword;
    private EditText newPassword;
    private EditText repeatPassword;
    private Button buttonListo;
    private ValidationUtils.PasswordValidator passwordValidator;
    private Context context;

    public EditPasswordFragment() {
        // Required empty public constructor
    }

    public static EditPasswordFragment newInstance() {
        return new EditPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editpassword_fragment, container, false);
        // Initialize views
        actualPassword = rootView.findViewById(R.id.actualPassword);
        newPassword = rootView.findViewById(R.id.newPassword);
        repeatPassword = rootView.findViewById(R.id.repeatPassword);
        buttonListo = rootView.findViewById(R.id.buttonListo);

        // Initialize PasswordValidator
        passwordValidator = new ValidationUtils.PasswordValidator();

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values entered by the user
                String actualPwd = actualPassword.getText().toString();
                String newPwd = newPassword.getText().toString();
                String repeatPwd = repeatPassword.getText().toString();

                // Validate actual password
                validateActualPassword(actualPwd);
            }
        });

        return rootView;
    }

    private void validateActualPassword(String actualPassword) {
        AuthService.getInstance().verifyPassword(actualPassword, new AuthService.AuthCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean isCorrect) {
                if (isCorrect) {
                    // Actual password is correct, proceed to validate new password
                    validateNewPassword();
                } else {
                    // Actual password is incorrect
                    Toast.makeText(getActivity(), "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                // Handle error
                Toast.makeText(getActivity(), "Error al verificar la contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateNewPassword() {
        // Get new password values
        String actualPwd=actualPassword.getText().toString();
        String newPwd = newPassword.getText().toString();
        String repeatPwd = repeatPassword.getText().toString();

        // Validate new password
        if (passwordValidator.isValidPassword(context,newPwd) && newPwd.equals(repeatPwd)) {
            // Update password
            AuthService.getInstance().updatePassword(actualPwd, newPwd, new AuthService.AuthCallback<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Toast.makeText(getActivity(), "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            if (!passwordValidator.isValidPassword(context,newPwd)) {
                Toast.makeText(getActivity(), "La nueva contraseña no es válida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Las nuevas contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
