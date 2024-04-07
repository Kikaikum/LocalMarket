package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserLobby extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lobby);

        // Inicializar AuthService
        authService = AuthService.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        if (id == R.id.nav_profile) {
            // Lógica para manejar el clic en el elemento "Perfil"
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_logout) {
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
                // El logout fue exitoso, puedes redirigir al usuario a la pantalla de inicio de sesión u otra pantalla apropiada
                Intent intent = new Intent(ActivityUserLobby.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                // Manejar cualquier error que ocurra durante el logout
                Toast.makeText(ActivityUserLobby.this, "Error al cerrar sesión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));
        productList.add(new Product(R.drawable.carrot_18, "zanahorias"));

        // Agregar más productos según sea necesario
        return productList;
    }
}
