package com.example.localmarket;

import android.content.Context;
import android.graphics.Color;
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
import com.example.localmarket.utils.ValidationUtils;

public class EditSurnameFragment extends Fragment {

    private EditText editTextSurname;
    private ImageView deleteIcon;
    private Button buttonListo; // Botón "Listo" para guardar los cambios
    private ValidationUtils.NameValidator surnameValidator; // Instancia de NameValidator
    private Context context;

    public EditSurnameFragment() {
        // Required empty public constructor
    }

    public static EditSurnameFragment newInstance(String surname) {
        EditSurnameFragment fragment = new EditSurnameFragment();
        Bundle args = new Bundle();
        args.putString("surname", surname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_surname, container, false);
        // Inicializar vistas
        editTextSurname = rootView.findViewById(R.id.editTextSurname);
        deleteIcon = rootView.findViewById(R.id.deleteIcon);
        buttonListo = rootView.findViewById(R.id.buttonListo);

        // Inicializar NameValidator
        surnameValidator = new ValidationUtils.NameValidator();

        // Obtener apellidos pasados desde EditProfileActivity
        String surname = getArguments().getString("surname");
        // Configurar el texto en el EditText
        editTextSurname.setText(surname);

        // Configurar el OnClickListener para el icono de eliminar
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del campo de texto
                editTextSurname.setText("");
            }
        });

        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nuevo valor de los apellidos de usuario
                String newSurname = editTextSurname.getText().toString();
                // Verificar los apellidos utilizando NameValidator
                if (surnameValidator.isValidName(context,newSurname)) {
                    // Los apellidos son válidos, llamar al método para actualizarlos
                    AuthService.getInstance().updateSurname(newSurname, new AuthService.AuthCallback<Void>() {
                        @Override
                        public void onSuccess(Void response) {
                            // Manejar el éxito, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Apellidos de usuario actualizados correctamente", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Manejar el error, mostrando un mensaje al usuario
                            Toast.makeText(getActivity(), "Error al actualizar los apellidos de usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Los apellidos no son válidos, mostrar un mensaje de error al usuario
                    Toast.makeText(getActivity(), "Apellidos no válidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}