package com.example.localmarket.activities;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localmarket.R;
import com.example.localmarket.fragments.OrderDetailsFragment;
import com.example.localmarket.model.CartItem;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.OrderManager;
import com.example.localmarket.utils.TokenManager;


import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    private AuthService authService;

    ArrayList<CartItem> cartItemList = new ArrayList<>();

    TokenManager tokenManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtocart_activity);

        authService = AuthService.getInstance();


        final int categoriaId;


        Intent intent = getIntent();
        if (intent != null) {
            // Obtener los datos del producto seleccionado
            String nombre = intent.getStringExtra("nombre");
            categoriaId = intent.getIntExtra("categoriaId", 0);
            String descripcion = intent.getStringExtra("descripcion");
            double precio = intent.getDoubleExtra("precio", 0.0);
            String tipoDePeso = intent.getStringExtra("tipoDePeso");
            int productId=intent.getIntExtra("productId",0);
            String vendedor=intent.getStringExtra("vendedor");


            ImageView imageProduct = findViewById(R.id.imageProduct);
            TextView textProductName = findViewById(R.id.textProductName);
            TextView textDescription = findViewById(R.id.textDescription);
            TextView textPrice = findViewById(R.id.textPrice);
            TextView textUnidadMedidaPrecio=findViewById(R.id.textUnidadMedidaPrecio);
            TextView textVendedor=findViewById(R.id.textFarmerUsername);

            // Inicializar los TextView de acuerdo al estado del Switch
            if (tipoDePeso.equalsIgnoreCase("peso")) {
                textUnidadMedidaPrecio.setText("/ Kg");
            } else {

                textUnidadMedidaPrecio.setText("/ Unidad");
            }

            imageProduct.setImageResource(categoriaId);
            textProductName.setText(nombre);
            textDescription.setText(descripcion);
            textPrice.setText(String.valueOf(precio));
            textVendedor.setText(vendedor);

        }

        //Configurar clic en el botón "Cancelar"
        Button buttonVolver = findViewById(R.id.btnVolver);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToLobby();
            }
        });

        Button buttonAddUnit = findViewById(R.id.btnAddUnit);
        buttonAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener referencia al TextView textViewquantity
                TextView textViewquantity = findViewById(R.id.textViewQuantity);

                // Obtener el valor actual del TextView
                String quantityText = textViewquantity.getText().toString();

                // Convertir el valor actual a un número entero
                int currentQuantity = Integer.parseInt(quantityText);

                // Sumar 1 al valor actual
                int newQuantity = currentQuantity + 1;

                // Establecer el nuevo valor en el TextView
                textViewquantity.setText(String.valueOf(newQuantity));
            }
        });


        // Configurar clic en el botón "Listo"
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Obtener la cantidad del TextView
                TextView textViewquantity = findViewById(R.id.textViewQuantity);
                TextView textWeight = findViewById(R.id.textUnidadMedidaPrecio);
                String quantityText = textViewquantity.getText().toString();
                int quantity = Integer.parseInt(quantityText);

                String nombre = intent.getStringExtra("nombre");
                int productId= intent.getIntExtra("productId",0);
                double precio = intent.getDoubleExtra("precio", 0.0);
                int categoriaId=intent.getIntExtra("categoriaId",0);
                String unidadMedida = textWeight.getText().toString();
                int idAgricultor=intent.getIntExtra("idAgricultor",2);


                Log.d("OrderDetailsFragment", "ID Agricultor paso1: " + idAgricultor);
                // Crear un nuevo elemento de carrito
                CartItem cartItem = new CartItem(idAgricultor,productId, categoriaId,nombre, precio, quantity,unidadMedida);

                // Obtener la instancia de OrderManager
                OrderManager orderManager = OrderManager.getInstance(AddToCartActivity.this);

                ArrayList<CartItem> cartItems = orderManager.getCartItems();

                // Agregar el nuevo elemento al carrito
                cartItems.add(cartItem);

                // Guardar la lista actualizada en las preferencias compartidas
                orderManager.saveCartItem(cartItems);

                // Notificar al usuario que el producto se ha añadido al carrito
                Toast.makeText(AddToCartActivity.this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show();

                // Redirigir al usuario a la pantalla de inicio
                redirectToLobby();
            }

        });

    }




    /**
     * Redirige al usuario a la pantalla de inicio.
     */
    private void redirectToLobby() {
        finish();
    }


}

