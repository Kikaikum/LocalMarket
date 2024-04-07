package com.example.localmarket.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Esta actividad permite al usuario editar su perfil.
 *@author Ainoha
 */

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button editUsernameButton, editEmailButton, editPasswordButton, deleteAccountButton, editNameButton , editSurnameButton;

    private User user;

    private ApiService apiService;
    private AuthService authService;
    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

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
                editPasswordButton.setText("**********");
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores al obtener el perfil del usuario
                //  mostrar un mensaje de error al usuario o realizar acciones de recuperación
                Log.e("EditProfileActivity", "Error al obtener el perfil del usuario: " + t.getMessage());
                Toast.makeText(EditProfileActivity.this, "Error al obtener el perfil del usuario", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            // Handle "Inicio" option
            // Redirect to home activity (ActivitySellerLobby or ActivityUserLobby based on user role)
            redirectToHome();
            return true;
        } else if (id == R.id.action_logout) {
            // Handle "Cerrar sesión" option
            showLogoutConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void logout() {
        // Perform logout operation
        authService.logoutUser(new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Logout successful, redirect to login screen
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                // Handle logout error
                Toast.makeText(EditProfileActivity.this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToHome() {
        Intent intent;
        if (user != null && user.getAgricultor()) {
            intent = new Intent(EditProfileActivity.this, ActivitySellerLobby.class);
        } else {
            intent = new Intent(EditProfileActivity.this, ActivityUserLobby.class);
        }
        startActivity(intent);
        finish();
    }
    /**
     * Método para abrir un fragmento en el contenedor y pasar datos.
     *
     * @param fragment El fragmento a abrir.
     */
    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Método para eliminar la cuenta del usuario.
     */
    private void deleteAccount() {
        // Obtener el id de usuario y el token de autenticación
        int userId = tokenManager.getUserId();
        String authToken = tokenManager.getToken();

        authService.deleteAccount(userId, authToken, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La cuenta se eliminó exitosamente
                Toast.makeText(EditProfileActivity.this, "La cuenta se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();

                // Redirigir al usuario a la pantalla de inicio de sesión u otra pantalla relevante
               redirectToLogin();
            }

            @Override
            public void onError(Throwable t) {
                // Error al eliminar la cuenta, manejar según sea necesario
                String errorMessage = "Error al eliminar la cuenta";
                if (t != null && t.getMessage() != null) {
                    errorMessage += ": " + t.getMessage();
                }
                Toast.makeText(EditProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Método para mostrar un diálogo de confirmación para eliminar la cuenta del usuario.
     */
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
    /**
     * Redirige al usuario a la pantalla de inicio de sesión.
     */
    private void redirectToLogin() {
        // Crea un Intent para iniciar la actividad de inicio de sesión
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent); // Inicia la actividad de inicio de sesión
        finish(); // Finaliza la actividad actual para evitar que el usuario vuelva atrás
    }



}
