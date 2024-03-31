package com.example.localmarket.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.localmarket.R;
import com.example.localmarket.fragments.EditEmailFragment;
import com.example.localmarket.fragments.EditNameFragment;
import com.example.localmarket.fragments.EditPasswordFragment;
import com.example.localmarket.fragments.EditSurnameFragment;
import com.example.localmarket.fragments.EditUsernameFragment;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

public class EditProfileActivity extends AppCompatActivity {
    private Button editUsernameButton, editEmailButton, editPasswordButton, deleteAccountButton, editNameButton , editSurnameButton;

    private User user;

    private ApiService apiService;
    private AuthService authService;
    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Inicializar vistas
        editUsernameButton = findViewById(R.id.editUsernameButton);
        editEmailButton = findViewById(R.id.editEmailButton);
        editPasswordButton = findViewById(R.id.editPasswordButton);
        deleteAccountButton=findViewById(R.id.deleteAccountButton);
        editNameButton=findViewById(R.id.textName);
        editSurnameButton=findViewById(R.id.textSurname);

        // Inicializar AuthService y ApiService
        authService = AuthService.getInstance();
        apiService = authService.getApiService();
        // Inicialización de tokenManager
        tokenManager = TokenManager.getInstance(getApplicationContext());




        // Obtener datos del usuario
        int userId = tokenManager.getUserId();
        authService.getUserProfile(userId, new AuthService.ProfileCallback() {

            @Override
            public void onSuccess(User userProfile) {
                user = userProfile;
                // Establecer los textos de los botones con los datos del usuario
                editNameButton.setText(userProfile.getName());
                editSurnameButton.setText(userProfile.getSurname());
                editUsernameButton.setText(userProfile.getUsername());
                editEmailButton.setText(userProfile.getEmail());
                editPasswordButton.setText(userProfile.getPassword());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores al obtener el perfil del usuario
            }
        });
        String username= tokenManager.getUserUsername();
        authService.getUserData(username, new AuthService.ProfileCallback() {

            @Override
            public void onSuccess(User userProfile) {
                user = userProfile;
                // Establecer los textos de los botones con los datos del usuario

                editPasswordButton.setText(userProfile.getPassword());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores al obtener el perfil del usuario
            }
        });



        // Configurar clics en los botones de edición
        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nombre actual del usuario
                String currentName = user.getName();
                // Abrir el fragmento EditNameFragment y pasar el nombre como argumento
                openFragment(EditNameFragment.newInstance(currentName));
            }
        });

        editSurnameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentSurname=user.getSurname();
                openFragment(EditSurnameFragment.newInstance(currentSurname));
            }
        });

        editUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUsername=user.getUsername();
                openFragment(EditUsernameFragment.newInstance(currentUsername));
            }
        });

        editEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentEmail=user.getEmail();
                openFragment(EditEmailFragment.newInstance(currentEmail));
            }
        });

        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la contraseña actual del usuario del perfil
                String currentPassword = user.getPassword();
                openFragment(new EditPasswordFragment());
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(); // Mostrar el diálogo de confirmación cuando se hace clic en el botón deleteAccountButton
            }
        });
    }

    // Método para abrir un fragmento en el contenedor y pasar datos
    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void deleteAccount() {
        authService.deleteAccount(new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(EditProfileActivity.this, "La cuenta se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                // La cuenta se eliminó exitosamente
                // Redirigir al usuario a la pantalla de inicio de sesión u otra pantalla relevante
                //redirectToLogin();
            }

            @Override
            public void onError(Throwable t) {
                // Error al eliminar la cuenta, manejar según sea necesario
                Toast.makeText(EditProfileActivity.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar cuenta");
        builder.setMessage("¿Está seguro de que desea eliminar su cuenta? Esta acción no se puede deshacer.");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario hace clic en "Eliminar", llamar al método para eliminar la cuenta
                deleteAccount();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario hace clic en "Cancelar", cerrar el diálogo
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
