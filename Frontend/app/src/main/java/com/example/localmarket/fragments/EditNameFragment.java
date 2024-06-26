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
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;
import com.example.localmarket.utils.ValidationUtils;
/**
 * Fragmento para editar el nombre del usuario.
 * @author Ainoha
 */
public class EditNameFragment extends Fragment {

    private EditText editTextName;
    private ImageView deleteIcon;
    private Button buttonListo; // Botón "Listo" para guardar los cambios
    private ValidationUtils.NameValidator nameValidator; // Instancia de NameValidator
    private Context context;
    private TokenManager tokenManager;
    /**
     * Constructor por defecto de la clase EditNameFragment.
     */
    public EditNameFragment() {
        // Required empty public constructor
    }
    /**
     * Metodo estatico para crear una nueva instancia del fragmento EditNameFragment con un nombre específico.
     *
     * @param name El nombre actual del usuario.
     * @return Una nueva instancia de EditNameFragment.
     */
    public static EditNameFragment newInstance(String name) {
        EditNameFragment fragment = new EditNameFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_name, container, false);
        // Inicializar vistas
        editTextName = rootView.findViewById(R.id.editTextName);
        deleteIcon = rootView.findViewById(R.id.deleteIcon);
        buttonListo = rootView.findViewById(R.id.buttonListo);

        // Inicializar NameValidator
        nameValidator = new ValidationUtils.NameValidator();
        tokenManager = new TokenManager(getActivity());

        // Set the context of the fragment
        context = getActivity();


        // Obtener el nombre  pasado desde EditProfileActivity
        String name = getArguments().getString("name");
        // Configurar el texto en el EditText
        editTextName.setText(name);

        // Configurar el OnClickListener para el icono de eliminar
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del campo de texto
                editTextName.setText("");
            }
        });

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nuevo valor del nombre
                String newName = editTextName.getText().toString();
                int id = tokenManager.getUserId();
                String token = tokenManager.getToken(); // Obtener el token de autenticación del usuario

                // Verificar el nombre de usuario utilizando NameValidator
                if (nameValidator.isValidName(context, newName)) {
                    UpdateNameRequest updateNameRequest = new UpdateNameRequest(id, newName, token);
                    // El nombre de usuario es válido, llamar al método para actualizarlo
                    AuthService.getInstance().updateName(id, updateNameRequest, token, new AuthService.AuthCallback<Void>() {
                        @Override
                        public void onSuccess(Void response) {
                            // Manejar el éxito, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "El Nombre ha sido actualizado correctamente", Toast.LENGTH_SHORT).show();

                            // Después de actualizar los datos, recarga la actividad
                            getActivity().recreate();
                            // Volver a la actividad EditProfile
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Manejar el error, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // El nombre de usuario no es válido, mostrar un mensaje de error al usuario
                    Toast.makeText(getActivity(), "Nombre no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }
}