package com.example.localmarket.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.example.localmarket.utils.ValidationUtils;
/**
 * Fragmento para editar el email del usuario.
 * @author Ainoha
 */

public class EditEmailFragment extends Fragment {

    private EditText editTextEmail;
    private ImageView deleteIcon;
    private Button buttonListo; // Botón "Listo" para guardar los cambios
    private ValidationUtils.EmailValidator emailValidator; // Instancia de EmailValidator
    private Context context;

    private TokenManager tokenManager;

    /**
     * Constructor por defecto de la clase EditEmailFragment.
     */
    public EditEmailFragment() {
        // Required empty public constructor
    }

    /**
     *Metodo estatico para crear una nueva instancia del fragmento EditEmailFragment con un email específico.
     *
     * @param email El email actual del usuario.
     * @return Una nueva instancia de EditEmailFragment.
     */
    public static EditEmailFragment newInstance(String email) {
        EditEmailFragment fragment = new EditEmailFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editemail_fragment, container, false);
        // Inicializar vistas
        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        deleteIcon = rootView.findViewById(R.id.deleteIcon);
        buttonListo = rootView.findViewById(R.id.buttonListo);

        // Obtener el contexto del fragmento
        context = getContext();
        // Inicializar EmailValidator
        emailValidator = new ValidationUtils.EmailValidator();
        tokenManager = new TokenManager(getActivity());

        // Obtener el email de usuario pasado desde EditProfileActivity
        String email = getArguments().getString("email");
        // Configurar el texto en el EditText
        editTextEmail.setText(email);

        // Configurar el OnClickListener para el icono de eliminar
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del campo de texto
                editTextEmail.setText("");
            }
        });

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nuevo valor del email de usuario
                String newEmail = editTextEmail.getText().toString();

                int id = tokenManager.getUserId();
                String token = tokenManager.getToken(); // Obtener el token de autenticación del usuario

                // Verificar el email de usuario utilizando EmailValidator
                if (emailValidator.isValidEmail(context, newEmail)) {

                    // Crear un objeto UpdateEmailRequest con el ID del usuario y el nuevo email de usuario
                    UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(id, newEmail, token);

                    // El email es válido, llamar al método para actualizarlo
                    AuthService.getInstance().updateEmail(id, updateEmailRequest, token, new AuthService.AuthCallback<Void>() {
                        @Override
                        public void onSuccess(Void response) {
                            // Manejar el éxito, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Email actualizado correctamente", Toast.LENGTH_SHORT).show();

                            // Después de actualizar los datos, recarga la actividad
                            getActivity().recreate();
                            // Volver a la actividad EditProfile
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Manejar el error, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Error al actualizar el Email", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // El email de usuario no es válido, mostrar un mensaje de error al usuario
                    Toast.makeText(getActivity(), "Email de usuario no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }
}