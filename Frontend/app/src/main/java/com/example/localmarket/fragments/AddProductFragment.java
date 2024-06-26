package com.example.localmarket.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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
import android.widget.Toast;

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


    public Spinner spinnerImages;
    private List<Product> productList;
    Switch switchMeasurement;
    private TokenManager tokenManager;
    private AuthService authService;

    public TextInputEditText itPrice, itDescription, itStock;
    public TextView etSwitch;
    public TextView etStock;
    private TextView etPriceCurrency;
    private FragmentActivity activity;
    private Context context;


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
        etStock = view.findViewById(R.id.tvUnidadMedida2);
        etSwitch = view.findViewById(R.id.tvUnidadMedida);
        etPriceCurrency = view.findViewById(R.id.tvPriceCurrency);

        etSwitch.setText("<-- Escoja unidad de \n\t\t\t\tmedida");
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
        itPrice = view.findViewById(R.id.itPrice);
        itDescription = view.findViewById(R.id.etDescription);
        itStock = view.findViewById(R.id.itStock);


        // Configura el listener para el botón Aceptar
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProduct();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClick();
            }
        });

        return view;
    }

    public void setAuthService(AuthService mockAuthService) {
        this.authService = mockAuthService;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public Object getItPrice() {
        return itPrice;
    }

    public Object getItDescription() {
        return itDescription;
    }

    public Object getItStock() {
        return itStock;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    // Método setter para establecer el contexto
    public void setContext(Context context) {
        this.context = context;
    }

    public void setSpinnerImages(Spinner spinner) {
        this.spinnerImages = spinner;
    }

    public void setDescriptionEditText(TextInputEditText descriptionEditText) {
        this.itDescription = descriptionEditText;
    }

    public void setSwitchEditText(EditText switchEditText) {
        this.etSwitch = switchEditText;
    }

    public void setSelectedProduct(Product selectedProduct) {
    }

    public void onCancelButtonClick() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }


    public interface OnProductAddedListener {
        void onProductAdded(Product product);
    }

    public void sendProduct() {
        System.out.println("Inicio de sendProduct");

        // Verifica el producto seleccionado en el spinner
        Product selectedProduct = (Product) spinnerImages.getSelectedItem();
        if (selectedProduct == null) {
            System.out.println("No hay producto seleccionado en el spinner");
            return;
        }
        System.out.println("Producto seleccionado: " + selectedProduct.getName());

        // Verificación de itDescription
        if (itDescription == null || itDescription.getText() == null) {
            System.out.println("itDescription o itDescription.getText() es null");
            return;
        }
        String description = itDescription.getText().toString();
        System.out.println("Descripción obtenida: " + description);

        // Verificación de etSwitch
        if (etSwitch == null || etSwitch.getText() == null) {
            System.out.println("etSwitch o etSwitch.getText() es null");
            return;
        }
        String unidadMedida = etSwitch.getText().toString();
        System.out.println("Unidad de medida obtenida: " + unidadMedida);

        // Intenta parsear precio
        double price = 0;
        if (itPrice == null || itPrice.getText() == null) {
            System.out.println("itPrice o itPrice.getText() es null");
            return;
        }
        try {
            price = Double.parseDouble(itPrice.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el precio");
            return;
        }
        System.out.println("Precio parseado: " + price);

        // Intenta parsear stock
        double stock = 0;
        if (itStock == null || itStock.getText() == null) {
            System.out.println("itStock o itStock.getText() es null");
            return;
        }
        try {
            stock = Double.parseDouble(itStock.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el stock");
            return;
        }
        System.out.println("Stock parseado: " + stock);

        // Contexto y manejo de TokenManager
        Context context = getActivity();
        if (context == null) {
            System.out.println("Context es null");
            return;
        }
        TokenManager tokenManager = TokenManager.getInstance(context);
        int idAgricultor = tokenManager.getUserId();
        String token = tokenManager.getToken();

        System.out.println("idAgricultor: " + idAgricultor + ", token: " + token);

        // Crear ProductRequest
        ProductRequest productRequest = new ProductRequest(selectedProduct.getName(), selectedProduct.getCategoriaId(), price, unidadMedida, description, idAgricultor, stock, token);
        System.out.println("ProductRequest creado");
        AuthService.getInstance().addProduct(idAgricultor, productRequest, token, new AuthService.AuthCallback<ProductResponse>() {
            @Override
            public void onSuccess(ProductResponse response) {
                Log.i("ProductAdd", "Producto añadido con éxito. ID: " + response.getId());
                Toast.makeText(context, "Producto registrado exitosamente", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, new SellerProductFragment())
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("ProductAdd", "Error al añadir producto: " + t.getMessage());
                Toast.makeText(context, "Error al registrar el producto", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void initializeProductList() {
        productList = new ArrayList<>();
        // Aquí agregarías cada producto a la lista
        productList.add(new Product(R.drawable.apple_whole_18, "Manzanas"));
        productList.add(new Product(R.drawable.aubergine_18, "Berengenas"));
        productList.add(new Product(R.drawable.avocado_18, "Aguacates"));
        productList.add(new Product(R.drawable.banana_18, "Bananas"));
        productList.add(new Product(R.drawable.blueberries_18, "Arandanos"));
        productList.add(new Product(R.drawable.carrot_18, "Zanahoria"));
        productList.add(new Product(R.drawable.bread_18, "Pan"));
        productList.add(new Product(R.drawable.broccoli_18, "Brocoli"));
        productList.add(new Product(R.drawable.cherry_18, "Cerezas"));
        productList.add(new Product(R.drawable.citrus_18, "Naranjas"));
        productList.add(new Product(R.drawable.citrus_slice_18, "Mandarinas"));
        productList.add(new Product(R.drawable.coconut_18, "Coco"));
        productList.add(new Product(R.drawable.grape_18, "Uvas"));
        productList.add(new Product(R.drawable.jug_alt_18, "Aceite"));
        productList.add(new Product(R.drawable.kiwi_fruit_18, "Kiwis"));
        productList.add(new Product(R.drawable.leafy_green_18, "Col"));
        productList.add(new Product(R.drawable.lemon_18, "Limones"));
        productList.add(new Product(R.drawable.lettuce_18, "Lechuga"));
        productList.add(new Product(R.drawable.mango_18, "Mango"));
        productList.add(new Product(R.drawable.melon_alt_18, "Melón"));
        productList.add(new Product(R.drawable.mushroom_18, "Setas"));
        productList.add(new Product(R.drawable.olive_18, "Olivas"));
        productList.add(new Product(R.drawable.onion_18, "Cebollas"));
        productList.add(new Product(R.drawable.peach_18, "Melocotones"));
        productList.add(new Product(R.drawable.peanuts_18, "Cacahuetes"));
        productList.add(new Product(R.drawable.peapod_18, "Guisantes"));
        productList.add(new Product(R.drawable.pear_18, "Peras"));
        productList.add(new Product(R.drawable.pepper_alt_18, "Pimiento rojo"));
        productList.add(new Product(R.drawable.pepper_alt_18, "Pimiento verde"));
        productList.add(new Product(R.drawable.pepper_hot_18, "Guindillas"));
        productList.add(new Product(R.drawable.pineapple_18, "Piña"));
        productList.add(new Product(R.drawable.potato_18, "Patatas"));
        productList.add(new Product(R.drawable.strawberry_18, "Fresa"));
        productList.add(new Product(R.drawable.pumpkin_18, "Calabaza"));
        productList.add(new Product(R.drawable.radish_18, "Rabano"));
        productList.add(new Product(R.drawable.tomato_18, "Tomate"));
        productList.add(new Product(R.drawable.watermelon_18, "Sandia"));

    }

}