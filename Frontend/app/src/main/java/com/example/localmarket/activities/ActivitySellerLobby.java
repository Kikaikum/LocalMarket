package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.fragments.AddProductFragment;
import com.example.localmarket.fragments.SellerProductFragment;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.TokenManager;

import java.util.List;

/**
 * Actividad que representa el lobby del vendedor.
 * Muestra una lista de productos y permite al vendedor agregar, editar o eliminar productos.
 * Además, proporciona opciones de perfil y cierre de sesión.
 *
 * @author Oriol Estero Sanchez
 */
public class ActivitySellerLobby extends AppCompatActivity {

        private AuthService authService;
    private Button btnAdd;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private SellerProductFragment sellerProductFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_lobby);



        // Configuración de la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        authService = AuthService.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container2, new SellerProductFragment())
                    .commit();
        }
    }
        @Override
        protected void onResume () {
            super.onResume();
            // Actualizar los productos cada vez que la actividad se reanude
            updateProducts();
        }

        private void updateProducts () {
            // Obtener una instancia de TokenManager
            TokenManager tokenManager = TokenManager.getInstance(this);
            // Obtener el ID de usuario y el token de autenticación
            int userId = tokenManager.getUserId();
            String token = tokenManager.getToken();
            // Obtener el fragmento actual
            sellerProductFragment = (SellerProductFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container2);
            // Verificar si el fragmento actual es SellerProductFragment
            if (sellerProductFragment != null) {
                // Obtener los productos del servidor y actualizar la lista en el fragmento
                sellerProductFragment.getAllProducts(userId, token);
            }
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

    /**
     * Método para hacer visible el RecyclerView.
     */
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }


    }

