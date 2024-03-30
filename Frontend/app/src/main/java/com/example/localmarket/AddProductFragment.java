package com.example.localmarket;

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

import com.example.localmarket.utils.ProductSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;


public class AddProductFragment extends Fragment {

    private Spinner spinnerImages;
    private List<Product> productList;
    private Switch switchMeasurement;


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
        switchMeasurement.setText("Peso/unidades -> "); // Texto por defecto

        // Listener para el cambio de estado del Switch
        switchMeasurement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // El switch está "encendido" - mostrar "Peso"
                    switchMeasurement.setText("Peso");
                } else {
                    // El switch está "apagado" - mostrar "Unidades"
                    switchMeasurement.setText("Unidades");
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
            }
        });
        EditText editTextPrice = view.findViewById(R.id.itPrice);
        EditText editTextDescription = view.findViewById(R.id.etDescription);


        // Configura el listener para el botón Aceptar
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = editTextPrice.getText().toString();
                String description = editTextDescription.getText().toString();
                Product selectedProduct = (Product) spinnerImages.getSelectedItem();
                String switchText = switchMeasurement.getText().toString();

                //simulamos el envio al servidor
                Log.i("SendData", "Enviando datos al servidor...");
                Log.i("ProductInfo", "Producto: " + selectedProduct.getName());
                Log.i("ProductInfo", "Medición: " + switchText);
                Log.i("ProductInfo", "Precio: " + price);
                Log.i("ProductInfo", "Descripción: " + description);

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

