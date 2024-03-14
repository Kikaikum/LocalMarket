package com.example.localmarket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.localmarket.network.service.AuthService;


public class EditUsernameFragment extends Fragment {

    private EditText editTextUsername;
    private ImageView deleteIcon;
    private Button buttonListo; // Botón "Listo" para guardar los cambios

    public EditUsernameFragment() {
        // Required empty public constructor
    }

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
                // Obtener el nuevo valor del nombre de usuario
                String newUsername = editTextUsername.getText().toString();
                // Llamar al método para actualizar el nombre de usuario
                AuthService.getInstance().updateUsername(newUsername, new AuthService.AuthCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        // Manejar el éxito, por ejemplo, mostrando un mensaje al usuario
                        Toast.makeText(getActivity(), "Nombre de usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Manejar el error, por ejemplo, mostrando un mensaje al usuario
                        Toast.makeText(getActivity(), "Error al actualizar el nombre de usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return rootView;
    }
}