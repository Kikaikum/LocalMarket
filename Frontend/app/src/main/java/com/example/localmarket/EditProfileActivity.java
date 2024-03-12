package com.example.localmarket;

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

import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.network.service.AuthService;

public class EditProfileActivity extends AppCompatActivity {
    private Button editUsernameButton, editEmailButton, editPasswordButton, deleteAccountButton;
    private User user;

    private ApiService apiService;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Inicializar vistas
        editUsernameButton = findViewById(R.id.editUsernameButton);
        editEmailButton = findViewById(R.id.editEmailButton);
        editPasswordButton = findViewById(R.id.editPasswordButton);
        deleteAccountButton=findViewById(R.id.deleteAccountButton);

        // Inicializar AuthService y ApiService
        authService = new AuthService();
        apiService = authService.getApiService();

        // Obtener datos del usuario
        authService.getUserProfile(new AuthService.ProfileCallback() {
            @Override
            public void onSuccess(User userProfile) {
                user = userProfile;
                // Establecer los textos de los botones con los datos del usuario
                editUsernameButton.setText(userProfile.getUsername());
                editEmailButton.setText(userProfile.getEmail());
                editPasswordButton.setText(userProfile.getPassword());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores al obtener el perfil del usuario
            }
        });



        // Configurar clics en los botones de edición
        editUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new EditUsernameFragment());
            }
        });

        editEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new EditEmailFragment());
            }
        });

        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    // Método para abrir un fragmento en el contenedor
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
