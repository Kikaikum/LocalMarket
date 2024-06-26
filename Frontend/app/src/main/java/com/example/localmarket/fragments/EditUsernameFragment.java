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
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.SessionManager;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.example.localmarket.utils.ValidationUtils;
/**
 * Fragmento para editar el nombre de usuario.
 * @author Ainoha
 */
public class EditUsernameFragment extends Fragment {

    private EditText editTextUsername;
    private ImageView deleteIcon;
    private Button buttonListo; // Botón "Listo" para guardar los cambios
    private ValidationUtils.UsernameValidator usernameValidator; // Instancia de UsernameValidator
    private Context context;
    private TokenManager tokenManager;
    private SessionManager sessionManager;
    /**
     * Constructor por defecto de la clase EditUsernameFragment.
     */
    public EditUsernameFragment() {
        // Required empty public constructor
    }
    /**
     * Método estático para crear una nueva instancia del fragmento EditUsernameFragment con un nombre de usuario específico.
     *
     * @param username El nombre de usuario actual.
     * @return Una nueva instancia de EditUsernameFragment.
     */
    public static EditUsernameFragment newInstance(String username) {
        EditUsernameFragment fragment = new EditUsernameFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editusername_fragment, container, false);
        // Inicializar vistas
        editTextUsername = rootView.findViewById(R.id.editTextUsername);
        deleteIcon = rootView.findViewById(R.id.deleteIcon);
        buttonListo = rootView.findViewById(R.id.buttonListo);


        // Inicializar UsernameValidator
        usernameValidator = new ValidationUtils.UsernameValidator();
        // Aquí inicializa el TokenManager
        tokenManager = new TokenManager(getActivity());


        // Obtener el nombre de usuario pasado desde EditProfileActivity
        String username = getArguments().getString("username");
        // Configurar el texto en el EditText
        editTextUsername.setText(username);

        // Configurar el OnClickListener para el icono de eliminar
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del campo de texto
                editTextUsername.setText("");
            }
        });
        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getContext();

                // Obtener el nuevo valor del nombre de usuario
                String newUsername = editTextUsername.getText().toString();
                int id = tokenManager.getUserId();
                String token = tokenManager.getToken(); // Obtener el token de autenticación del usuario

                // Verificar el nombre de usuario utilizando UsernameValidator
                if (usernameValidator.isValidUsername(context, newUsername)) {
                    // Crear un objeto UpdateUsernameRequest con el ID del usuario y el nuevo nombre de usuario
                    UpdateUsernameRequest updateUsernameRequest = new UpdateUsernameRequest(id, newUsername, token);

                    // El nombre de usuario es válido, llamar al método para actualizarlo
                    AuthService.getInstance().updateUsername(id, updateUsernameRequest, token, new AuthService.AuthCallback<Void>() {
                        @Override
                        public void onSuccess(Void response) {
                            // Manejar el éxito, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Nombre de usuario actualizado correctamente", Toast.LENGTH_SHORT).show();

                            // Después de actualizar los datos, recarga la actividad
                            getActivity().recreate();
                            // Volver a la actividad EditProfile
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Manejar el error, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Error al actualizar el nombre de usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // El nombre de usuario no es válido, mostrar un mensaje de error al usuario
                    Toast.makeText(getActivity(), "Nombre de usuario no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }
}