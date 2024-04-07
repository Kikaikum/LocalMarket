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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.fragments.AddProductFragment;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.ProductList;

import java.util.List;

public class ActivitySellerLobby extends AppCompatActivity  implements ProductAdapter.OnProductClickListener {

    private AuthService authService;
    private Button btnAdd;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_lobby);

        // Configuración del RecyclerView-Ainoha
        productList = ProductList.getProducts();
        adapter = new ProductAdapter(productList, this);
        recyclerView = findViewById(R.id.reciclerViewProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        // Configuración de la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        authService = AuthService.getInstance();

        btnAdd = findViewById(R.id.addProduct); // Asegúrate de que este ID exista en tu layout activity_seller_lobby.xml
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ocultar el RecyclerView
                recyclerView.setVisibility(View.GONE);

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

    //
    /**
     * Método de devolución de llamada invocado cuando se hace clic en un producto en el RecyclerView.
     * Abre EditProductActivity para editar el producto seleccionado.
     * @author Ainoha
     * @param product El producto seleccionado que se va a editar.
     */
    @Override
    public void onProductClick(Product product) {
        // Crea un Intent para abrir EditProductActivity
        Intent intent = new Intent(this, EditProductActivity.class);

        // Pasa los datos del producto seleccionado a EditProductActivity
        intent.putExtra("name", product.getName());
        intent.putExtra("imageId", product.getImageId());
        intent.putExtra("descripcion", product.getDescripcion());
        intent.putExtra("tipoDePeso", product.getTipoDePeso());
        intent.putExtra("precio", product.getPrecio());

        // Inicia la actividad
        startActivity(intent);
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
    // Método para hacer visible el RecyclerView
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }
}
