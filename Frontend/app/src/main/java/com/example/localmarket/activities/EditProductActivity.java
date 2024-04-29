package com.example.localmarket.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

/**
 * Actividad para editar un producto.
 * Esta actividad permite a los usuarios editar los detalles de un producto, como el nombre, la descripción, el precio y el stock.
 * Los usuarios también pueden eliminar el producto o guardar los cambios realizados.
  * @author ainoha
 */
public class EditProductActivity extends AppCompatActivity {


    private TokenManager tokenManager;
    private AuthService authService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);

        authService = AuthService.getInstance();
        tokenManager = TokenManager.getInstance(this);

        final int categoriaId;
        final int productId;

        Intent intent = getIntent();
        if (intent != null) {
            // Obtener los datos del producto seleccionado
            String nombre = intent.getStringExtra("nombre");
            categoriaId = intent.getIntExtra("categoriaId", 0);
            String descripcion = intent.getStringExtra("descripcion");
            String tipoDePeso = intent.getStringExtra("tipoDePeso");
            double precio = intent.getDoubleExtra("precio", 0.0);
            double stock = intent.getDoubleExtra("stock", 0);

            ImageView imageProduct = findViewById(R.id.imageProduct);
            EditText editTextName = findViewById(R.id.editTextName);
            EditText editTextDescription = findViewById(R.id.editTextDescription);
            EditText editTextWeight = findViewById(R.id.editTextUnidadMedida);
            EditText editTextPrice = findViewById(R.id.editTextPrice);
            EditText editTextStock = findViewById(R.id.editTextStock);
            TextView textViewUnidadMedidaStock=findViewById(R.id.textUnidadMedidaStock);
            TextView textViewUnidadMedidaPrecio=findViewById(R.id.textUnidadMedidaPrecio);

            imageProduct.setImageResource(categoriaId);
            editTextName.setText(nombre);
            editTextDescription.setText(descripcion);
            editTextWeight.setText(tipoDePeso);
            editTextPrice.setText(String.valueOf(precio));
            editTextStock.setText(String.valueOf(stock));

            // Configurar el estado inicial del Switch
            Switch switchUnidadMedida = findViewById(R.id.switchUnidadMedida);
            switchUnidadMedida.setChecked(tipoDePeso.equalsIgnoreCase("peso"));
            // Inicializar los TextView de acuerdo al estado del Switch
            if (tipoDePeso.equalsIgnoreCase("peso")) {
                textViewUnidadMedidaStock.setText("Kg");
                textViewUnidadMedidaPrecio.setText("/ Kg");
            } else {
                textViewUnidadMedidaStock.setText("Unidades");
                textViewUnidadMedidaPrecio.setText("/ Unidad");
            }
            // Agregar listener para manejar cambios en el Switch
            switchUnidadMedida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    EditText editTextUnidadMedida = findViewById(R.id.editTextUnidadMedida);
                    if (isChecked) {
                        // Si el Switch está activado (peso), establecer unidad de medida como "peso"
                        editTextUnidadMedida.setText("peso");
                        textViewUnidadMedidaStock.setText("Kg");
                        textViewUnidadMedidaPrecio.setText("/ Kg");
                    } else {
                        // Si el Switch está desactivado (unidades), establecer unidad de medida como "unidades"
                        editTextUnidadMedida.setText("unidades");
                        textViewUnidadMedidaStock.setText("Unidades");
                        textViewUnidadMedidaPrecio.setText("/ Unidad");
                    }
                }
            });
        } else {
            // Asignar valores predeterminados si la Intent es nula
            categoriaId = 0;
            productId = 0;
        }

        //Configurar clic en el botón "Cancelar"
        Button buttonCancelar=findViewById(R.id.btnCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToLobby();
            }
        });

        // Configurar clic en el botón "Listo"
        Button buttonListo = findViewById(R.id.btnGuardar);
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
                EditText editTextWeight = findViewById(R.id.editTextUnidadMedida);
                EditText editTextPrice = findViewById(R.id.editTextPrice);
                EditText editTextStock=findViewById(R.id.editTextStock);

                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String unidadMedida = editTextWeight.getText().toString();
                double precio = Double.parseDouble(editTextPrice.getText().toString());
                int productId = tokenManager.getProductId();


                double stock = Double.parseDouble(editTextStock.getText().toString());

                // Crear un objeto Product con los datos actualizados
                Product updatedProduct = new Product(name,categoriaId, precio, description, unidadMedida, stock);



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
        ImageView buttonEliminarProducto = findViewById(R.id.deleteIcon);
        buttonEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(); // Mostrar el diálogo de confirmación cuando se hace clic en el botón borrar producto
            }
        });


    }

    /**
     * Elimina el producto actualmente en edición.
     * Este método solicita al servicio de autenticación que elimine el producto actualmente en edición.
     * Después de eliminar el producto, redirige al usuario a la pantalla de inicio.
     */
    private void deleteProduct() {
        // Obtener el id de producto y el token de autenticación
        int productId = tokenManager.getProductId();
        String authToken = tokenManager.getToken();

        authService.deleteProduct(productId, authToken, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // El producto se eliminó exitosamente
                Toast.makeText(EditProductActivity.this, "El producto se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();

                // Redirigir al usuario a la pantalla de inicio
                redirectToLobby();
            }

            @Override
            public void onError(Throwable t) {
                // Error al eliminar producto,
                String errorMessage = "Error al eliminar producto";
                if (t != null && t.getMessage() != null) {
                    errorMessage += ": " + t.getMessage();
                }
                Toast.makeText(EditProductActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para mostrar un diálogo de confirmación para eliminar producto
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar producto");
        builder.setMessage("¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario hace clic en "Eliminar", llamar al método para eliminar producto
                deleteProduct();
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
     * Redirige al usuario a la pantalla de inicio.
     */
    private void redirectToLobby() {

        Intent intent = new Intent(this, ActivitySellerLobby.class);
        startActivity(intent);
    }


}