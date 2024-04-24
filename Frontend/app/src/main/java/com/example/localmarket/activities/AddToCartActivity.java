package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localmarket.R;
import com.example.localmarket.network.service.AuthService;

public class AddToCartActivity extends AppCompatActivity {

    private AuthService authService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtocart_activity);

        authService = AuthService.getInstance();


        final int categoriaId;
        final int productId;

        Intent intent = getIntent();
        if (intent != null) {
            // Obtener los datos del producto seleccionado
            String nombre = intent.getStringExtra("nombre");
            categoriaId = intent.getIntExtra("categoriaId", 0);
            String descripcion = intent.getStringExtra("descripcion");
            double precio = intent.getDoubleExtra("precio", 0.0);
            String tipoDePeso = intent.getStringExtra("tipoDePeso");


            ImageView imageProduct = findViewById(R.id.imageProduct);
            TextView textProductName = findViewById(R.id.textProductName);
            TextView textDescription = findViewById(R.id.textDescription);
            TextView textPrice = findViewById(R.id.textPrice);
            TextView textUnidadMedidaPrecio=findViewById(R.id.textUnidadMedidaPrecio);

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
                // Obtener el token del TokenManager

            }


        });

    }




            /**
             * Redirige al usuario a la pantalla de inicio.
             */
            private void redirectToLobby() {

                Intent intent = new Intent(this, ActivityUserLobby.class);
                startActivity(intent);
            }


        }

