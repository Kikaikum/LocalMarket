package com.example.localmarket.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.localmarket.activities.ActivitySellerLobby;
import com.example.localmarket.model.Product;
import com.example.localmarket.R;
import com.example.localmarket.model.ProductRequest;
import com.example.localmarket.model.ProductResponse;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductSpinnerAdapter;
import com.example.localmarket.utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class AddProductFragment extends Fragment {

    private Spinner spinnerImages;
    private List<Product> productList;
    private Switch switchMeasurement;

    private TextInputEditText itPrice, itDescription, itStock;
    private TextView etSwitch, etStock, etPriceCurrency;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        // Inicializar la lista de productos con imágenes
        initializeProductList();

        // Configurar el Spinner y el adaptador
        spinnerImages = view.findViewById(R.id.spinnerProduct);
        ProductSpinnerAdapter adapter = new ProductSpinnerAdapter(getActivity(), productList);
        spinnerImages.setAdapter(adapter);

        // Listener para cuando se selecciona un item del spinner
        spinnerImages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Puedes obtener el producto seleccionado así:
                Product selectedProduct = (Product) parent.getItemAtPosition(position);
                // Haz algo con el producto seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Algún código aquí si necesitas manejar la no selección
            }
        });
        switchMeasurement = view.findViewById(R.id.switchPesoUnidad);
        etStock= view.findViewById(R.id.tvUnidadMedida2);
        etSwitch = view.findViewById(R.id.tvUnidadMedida);
        etPriceCurrency = view.findViewById(R.id.tvPriceCurrency);

        etSwitch.setText("<-- Selecione unidad de\n\t\t\t medida");
        etStock.setText("");
        etPriceCurrency.setText("€");

        // Listener para el cambio de estado del Switch
        switchMeasurement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // El switch está "encendido" - mostrar "Peso"
                    etSwitch.setText("Peso");
                    etStock.setText("Kg");
                } else {
                    // El switch está "apagado" - mostrar "Unidades"
                    etSwitch.setText("Unidades");
                    etStock.setText("Unidad/es");
                }
            }
        });
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnAccept = view.findViewById(R.id.btnAcept);

        // Configura el listener para el botón Cancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manejar clic en botón Cancelar
                // Por ejemplo, podrías cerrar el fragmento o limpiar los campos de texto
                getActivity().getSupportFragmentManager().popBackStack();

                // Notificar a la actividad que el fragmento se ha cerrado
                if (getActivity() instanceof ActivitySellerLobby) {
                    ((ActivitySellerLobby) getActivity()).showRecyclerView();
                }
            }
        });
        TextInputEditText editTextPrice = view.findViewById(R.id.itPrice);
        TextInputEditText editTextDescription = view.findViewById(R.id.etDescription);
        TextInputEditText editTextStock = view.findViewById(R.id.itStock);


        // Configura el listener para el botón Aceptar
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Suponiendo que tienes un EditText para el nombre y otro para el precio
                //String name = ((Product) spinnerImages.getSelectedItem()).getName();
                String name = "bannana@bannana.com";
                int categoriaId = ((Product) spinnerImages.getSelectedItem()).getCategoriaId();
                String description = editTextDescription.getText().toString();
                String unidadMedida = etSwitch.getText().toString();
                double price = 0;
                double stock = 0;
                Context context = getActivity();
                TokenManager tokenManager = TokenManager.getInstance(context);
                int idAgricultor = tokenManager.getUserId();

                try {
                    price = Double.parseDouble(editTextPrice.getText().toString());
                    stock = Double.parseDouble(editTextStock.getText().toString());

                } catch (NumberFormatException e) {
                    Log.e("AddProductFragment", "Error al parsear el precio", e);
                    // Manejar error, por ejemplo, mostrando un mensaje al usuario
                }

                // Crear ProductRequest y enviarlo
                ProductRequest productRequest = new ProductRequest(name, categoriaId, price, unidadMedida,description, idAgricultor,stock);

                AuthService.getInstance().addProduct(productRequest, new AuthService.AuthCallback<ProductResponse>() {
                    @Override
                    public void onSuccess(ProductResponse response) {
                        Log.i("ProductAdd", "Producto añadido con éxito. ID: " + response.getId());
                        Log.i("ProductAdd", "Nombre: " + response.getNombre());
                        Log.i("ProductAdd", "Categoría ID: " + response.getCategoriaId());
                        Log.i("ProductAdd", "Descripción: " + response.getDescripcion());
                        Log.i("ProductAdd", "Unidad de medida: " + response.getUnidadMedida());
                        Log.i("ProductAdd", "Precio: " + response.getPrecio());
                        Log.i("ProductAdd", "Stock: " + response.getStock());

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("ProductAdd", "Error al añadir producto: " + t.getMessage());
                        
                    }
                });

                //simulamos el envio al servidor
                //Log.i("SendData", "Enviando datos al servidor...");
                //Log.i("ProductInfo", "Producto: " + selectedProduct.getName());
                //Log.i("ProductInfo", "Medición: " + switchText);
                //Log.i("ProductInfo", "Precio: " + price);
                //Log.i("ProductInfo", "Descripción: " + description);

            }
        });

        return view;
    }

    private void initializeProductList() {
        productList = new ArrayList<>();
        // Aquí agregarías cada producto a la lista
        productList.add(new Product(R.drawable.apple_whole_18,"Manzanas" ));
        productList.add(new Product(R.drawable.aubergine_18,"Berengenas" ));
        productList.add(new Product(R.drawable.avocado_18,"Aguacates" ));
        productList.add(new Product(R.drawable.banana_18,"Bananas" ));
        productList.add(new Product(R.drawable.blueberries_18,"Arandanos" ));
        productList.add(new Product(R.drawable.carrot_18,"Zanahoria" ));
        productList.add(new Product(R.drawable.bread_18,"Pan" ));
        productList.add(new Product(R.drawable.broccoli_18,"Brocoli" ));
        productList.add(new Product(R.drawable.cherry_18,"Cerezas" ));
        productList.add(new Product(R.drawable.citrus_18,"Naranjas" ));
        productList.add(new Product(R.drawable.citrus_slice_18,"Mandarinas" ));
        productList.add(new Product(R.drawable.coconut_18,"Coco" ));
        productList.add(new Product(R.drawable.grape_18,"Uvas" ));
        productList.add(new Product(R.drawable.jug_alt_18,"Aceite" ));
        productList.add(new Product(R.drawable.kiwi_fruit_18,"Kiwis" ));
        productList.add(new Product(R.drawable.leafy_green_18,"Col" ));
        productList.add(new Product(R.drawable.lemon_18,"Limones" ));
        productList.add(new Product(R.drawable.lettuce_18,"Lechuga" ));
        productList.add(new Product(R.drawable.mango_18,"Mango" ));
        productList.add(new Product(R.drawable.melon_alt_18,"Melón" ));
        productList.add(new Product(R.drawable.mushroom_18,"Setas" ));
        productList.add(new Product(R.drawable.olive_18,"Olivas" ));
        productList.add(new Product(R.drawable.onion_18,"Cebollas" ));
        productList.add(new Product(R.drawable.peach_18,"Melocotones" ));
        productList.add(new Product(R.drawable.peanuts_18,"Cacahuetes" ));
        productList.add(new Product(R.drawable.peapod_18,"Guisantes" ));
        productList.add(new Product(R.drawable.pear_18,"Peras" ));
        productList.add(new Product(R.drawable.pepper_alt_18,"Pimiento rojo" ));
        productList.add(new Product(R.drawable.pepper_alt_18,"Pimiento verde" ));
        productList.add(new Product(R.drawable.pepper_hot_18,"Guindillas" ));
        productList.add(new Product(R.drawable.pineapple_18,"Piña" ));
        productList.add(new Product(R.drawable.potato_18,"Patatas" ));
        productList.add(new Product(R.drawable.strawberry_18,"Fresa" ));
        productList.add(new Product(R.drawable.pumpkin_18,"Calabaza" ));
        productList.add(new Product(R.drawable.radish_18,"Rabano" ));
        productList.add(new Product(R.drawable.tomato_18,"Tomate" ));
        productList.add(new Product(R.drawable.watermelon_18,"Sandia" ));

    }
}

