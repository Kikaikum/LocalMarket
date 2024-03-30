package com.example.localmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.localmarket.network.service.AuthService;

public class ActivitySellerLobby extends AppCompatActivity {

    private AuthService authService;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_lobby);

        authService = AuthService.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        btnAdd = findViewById(R.id.addProduct); // Asegúrate de que este ID exista en tu layout activity_seller_lobby.xml
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductFragment fragment = new AddProductFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container2, fragment) // Asegúrate de que este contenedor exista en tu layout
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_agricultor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Manejar eventos de clic para cada elemento del menú
        if (id == R.id.nav_profile2) {
            // Lógica para manejar el clic en el elemento "Perfil"
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_logout2) {
            // Lógica para manejar el clic en el elemento "Cerrar sesión"
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void logout() {
        authService.logoutUser(new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Intent intent = new Intent(ActivitySellerLobby.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ActivitySellerLobby.this, "Error al cerrar sesión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
