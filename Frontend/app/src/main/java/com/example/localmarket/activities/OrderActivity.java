package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.fragments.OrderDetailsFragment;
import com.example.localmarket.network.service.AuthService;

public class OrderActivity extends AppCompatActivity {


    private AuthService authService;
    private RecyclerView recyclerView;
    private OrderDetailsFragment orderDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        // Configuración de la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        authService = AuthService.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerOrderActivity, new OrderDetailsFragment())
                    .commit();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
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
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(OrderActivity.this, "Error al cerrar sesión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para hacer visible el RecyclerView.
     */
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }


}


