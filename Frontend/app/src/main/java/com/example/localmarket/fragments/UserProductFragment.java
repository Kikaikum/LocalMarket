package com.example.localmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.activities.AddToCartActivity;
import com.example.localmarket.activities.EditProductActivity;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.ProductAdapterUserLobby;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class UserProductFragment extends Fragment implements ProductAdapterUserLobby.OnProductClickListener {
    private List<Integer> agricultoresIdsEnRango = new ArrayList<>();
    private List<Product> productosEnRango = new ArrayList<>();
    private AuthService authService;
    private List<Product> productList;
    private ProductAdapterUserLobby adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager;
    private View mapButton;
    private boolean isMapButtonVisible= true;
    //cambios


    public UserProductFragment() {
        // Constructor público requerido
    }

    /**
     * Crea una nueva instancia de UserProductFragment.
     *
     * @return Una nueva instancia de UserProductFragment.
     */
    public static UserProductFragment newInstance() {
        return new UserProductFragment();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenManager = TokenManager.getInstance(requireContext());
        authService = AuthService.getInstance();
        Bundle bundle2 =super.getArguments();
        if (bundle2 != null) {
            ArrayList<Integer> agricultoresIds = getArguments().getIntegerArrayList("agricultoresIds");
            if (agricultoresIds != null) {
                Log.d("UserProductFragment", "Recibido Bundle con " + agricultoresIds.size() + " IDs de agricultores");
                agricultoresIdsEnRango = agricultoresIds;
                // Cargar los productos de los agricultores en rango
                cargarProductosDeAgricultores();
            } else {
                Log.e("UserProductFragment", "La lista de IDs de agricultores está vacía");
            }
        } else {
            Log.e("UserProductFragment", "No se recibió el Bundle");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_product, container, false);

        // Inicializa el RecyclerView y el adaptador
        productList = new ArrayList<>();
        adapter = new ProductAdapterUserLobby(productList, this);
        recyclerView = view.findViewById(R.id.reciclerViewProductsUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mapButton= view.findViewById(R.id.buttonMapFragment);

        authService = AuthService.getInstance();
        tokenManager = new TokenManager(getActivity());


        showMapFragmentButton();
        cargarProductosDeAgricultores();

        return view;
    }


    /**
     * Obtiene todos los productos del vendedor desde el servidor.
     *
     * @author Ainoha
     */
    private void cargarProductosDeAgricultores() {
        String token = tokenManager.getToken();
        if (agricultoresIdsEnRango!= null) {
            for (int id : agricultoresIdsEnRango) {
                authService.getAllProducts(id, token, new AuthService.AuthCallback<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> productos) {
                        // Procesar los productos y mostrarlos en la interfaz
                        productosEnRango = productos;
                        mostrarProductosEnPantalla();
                        mapButton.setEnabled(false);
                        mapButton.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable error) {
                        // Manejar errores al cargar los productos
                        Log.e("UserProductFragment", "Error al obtener productos: " + error.getMessage());
                        Toast.makeText(getContext(), "Error al cargar productos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            showMapFragmentButton();
            // Manejar el caso en el que la lista de IDs de agricultores esté vacía
            Log.e("UserProductFragment", "La lista de IDs de agricultores está vacía");
            Toast.makeText(getContext(), "No hay agricultores en el rango", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMapFragmentButton() {
        // Código para mostrar el botón que abre el MapFragment


        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para abrir el MapFragment
                openMapFragment();
            }


        });
    }
    private void openMapFragment() {
        // Código para abrir el MapFragment
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerUserLobby, mapFragment);
        transaction.commit();
    }

    private void mostrarProductosEnPantalla() {
        if (!productList.isEmpty()) {
            // Hay productos, ocultar y deshabilitar el botón del MapFragment
            isMapButtonVisible = false;
            mapButton.setVisibility(View.GONE);
            mapButton.setEnabled(false);
        } else {
            // No hay productos, mostrar y habilitar el botón del MapFragment
            isMapButtonVisible = true;
            mapButton.setVisibility(View.VISIBLE);
            mapButton.setEnabled(true);
        }
        // Aquí debes implementar la lógica para mostrar los productos en la interfaz de usuario
        // por ejemplo, en una lista, en una grilla, etc.
        adapter = new ProductAdapterUserLobby(productosEnRango, this);
        recyclerView.setAdapter(adapter);
    }

    public void getAllProductsAvailable() {
        authService.getAllProductsAvailable(new AuthService.AuthCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                // Actualizar la lista de productos con los obtenidos del servidor
                productList.clear();
                productList.addAll(products);
                adapter.notifyDataSetChanged();
                // Guardar el ID del primer producto en el TokenManager
                if (!products.isEmpty()) {
                    int firstProductId = products.get(0).getProductId();
                    tokenManager.saveProductId(firstProductId);
                }
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores, por ejemplo, mostrar un mensaje de error al usuario
                Toast.makeText(getContext(), "Error al obtener productos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param product El producto en el que se hizo clic.
     * @author Ainoha
     */
    @Override
    public void onProductClick(Product product) {
        // Manejar el clic en un producto para abrir la actividad de edición
        Context context = getContext();
        int productId = product.getProductId();

        TokenManager tokenManager = TokenManager.getInstance(getContext());
        tokenManager.saveProductId(productId);

        if (context != null) {
            Intent intent = new Intent(context, AddToCartActivity.class);
            intent.putExtra("nombre", product.getName());
            intent.putExtra("categoriaId", product.getCategoriaId());
            intent.putExtra("descripcion", product.getDescripcion());
            intent.putExtra("precio", product.getPrecio());
            intent.putExtra("tipoDePeso", product.getUnidadMedida());
            intent.putExtra("productId", productId);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Error: No se puede abrir la actividad", Toast.LENGTH_SHORT).show();
        }
    }
}