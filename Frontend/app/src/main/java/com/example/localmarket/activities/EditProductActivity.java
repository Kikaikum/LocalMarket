package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

/**
 * Actividad para editar un producto.
 */
public class EditProductActivity extends AppCompatActivity {

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);

        // Inicializar TokenManager
        tokenManager = TokenManager.getInstance(this);

        // Declarar imageId como final para que sea accesible en el contexto del onClick
        final int imageId;

        // Recuperar los datos del producto seleccionado
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            imageId = intent.getIntExtra("imageId", 0); // Obtener el imageId del intent
            String descripcion = intent.getStringExtra("descripcion");
            String tipoDePeso = intent.getStringExtra("tipoDePeso");
            double precio = intent.getDoubleExtra("precio", 0.0);

            // Inicializar vistas y asignar los datos del producto seleccionado
            ImageView imageProduct = findViewById(R.id.imageProduct);
            EditText editTextName = findViewById(R.id.editTextName);
            EditText editTextDescription = findViewById(R.id.editTextDescription);
            EditText editTextWeight = findViewById(R.id.editTextWeight);
            EditText editTextPrice = findViewById(R.id.editTextPrice);

            imageProduct.setImageResource(imageId);
            editTextName.setText(name);
            editTextDescription.setText(descripcion);
            editTextWeight.setText(tipoDePeso);
            editTextPrice.setText(String.valueOf(precio));
        } else {
            // Asignar un valor predeterminado en caso de que la Intent sea nula
            imageId = 0; // O cualquier otro valor predeterminado que desees
        }

        // Configurar clic en el botón "Listo"
        Button buttonListo = findViewById(R.id.buttonListo);
        buttonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el token del TokenManager
                String token = tokenManager.getToken();
                if (token == null) {
                    // Si el token es nulo, probablemente el usuario no ha iniciado sesión
                    // Manejar esta situación según tus requerimientos (por ejemplo, redirigir al usuario a la pantalla de inicio de sesión)
                    // Aquí se muestra un mensaje de error
                    Toast.makeText(EditProductActivity.this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener los datos modificados del producto
                EditText editTextName = findViewById(R.id.editTextName);
                EditText editTextDescription = findViewById(R.id.editTextDescription);
                EditText editTextWeight = findViewById(R.id.editTextWeight);
                EditText editTextPrice = findViewById(R.id.editTextPrice);

                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String tipoDePeso = editTextWeight.getText().toString();
                double price = Double.parseDouble(editTextPrice.getText().toString());

                // Crear un objeto Product con los datos actualizados
                Product updatedProduct = new Product(imageId, name, description, tipoDePeso, price);

                // Obtener el ID del producto seleccionado
                int productId = intent.getIntExtra("productId", 0);

                // Llamar al método para actualizar el producto en el servidor
                AuthService.getInstance().updateProduct(productId, updatedProduct, token, new AuthService.AuthCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        // Mostrar mensaje de éxito
                        Toast.makeText(EditProductActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                        // Opcional: cerrar la actividad
                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Mostrar mensaje de error
                        Toast.makeText(EditProductActivity.this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Configurar clic en el botón "Eliminar Producto"
        Button buttonEliminarProducto = findViewById(R.id.buttonEliminarProducto);
        buttonEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el token del TokenManager
                String token = tokenManager.getToken();
                if (token == null) {
                    // Si el token es nulo, probablemente el usuario no ha iniciado sesión
                    // Manejar esta situación según tus requerimientos (por ejemplo, redirigir al usuario a la pantalla de inicio de sesión)
                    // Aquí se muestra un mensaje de error
                    Toast.makeText(EditProductActivity.this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener el ID del producto seleccionado
                int productId = intent.getIntExtra("productId", 0);

                // Llamar al método para eliminar el producto en el servidor
                AuthService.getInstance().deleteProduct(productId, token, new AuthService.AuthCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        // Mostrar mensaje de éxito
                        Toast.makeText(EditProductActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                        // Opcional: cerrar la actividad
                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Mostrar mensaje de error
                        Toast.makeText(EditProductActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
