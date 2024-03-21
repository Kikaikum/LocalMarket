package com.example.localmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.utils.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserLobby extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lobby);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtener la lista de productos
        productList = getProductList();
        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));
        productList.add(new Product(R.drawable.carrot_18,"zanahorias"));

        // Agregar más productos según sea necesario
        return productList;
    }
}


