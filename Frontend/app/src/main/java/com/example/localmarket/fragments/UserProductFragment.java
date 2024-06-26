package com.example.localmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.activities.AddToCartActivity;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapterUserLobby;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra los productos disponibles para el usuario.
 * @author Oriol + Ainoha
 */
public class UserProductFragment extends Fragment implements ProductAdapterUserLobby.OnProductClickListener {

    private List<Integer> agricultoresIdsEnRango = new ArrayList<>();
    private List<Product> productosEnRango = new ArrayList<>();
    private AuthService authService;
    private List<Product> productList;
    private ProductAdapterUserLobby adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager;
    private View mapButton;
    private boolean isMapButtonVisible = true;

    /**
     * Constructor público requerido.
     */
    public UserProductFragment() {
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
        Bundle bundle2 = super.getArguments();
        if (bundle2 != null) {
            ArrayList<Integer> agricultoresIds = getArguments().getIntegerArrayList("agricultoresIds");
            if (agricultoresIds != null) {
                Log.d("UserProductFragment", "Recibido Bundle con " + agricultoresIds.size() + " IDs de agricultores");
                agricultoresIdsEnRango.addAll(agricultoresIds);
            } else {
                Log.e("UserProductFragment", "La lista de IDs de agricultores está vacía");
            }
        } else {
            Log.e("UserProductFragment", "No se recibió el Bundle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_product, container, false);

        // Inicializa el RecyclerView y el adaptador
        productList = new ArrayList<>();
        adapter = new ProductAdapterUserLobby(productList, this);
        recyclerView = view.findViewById(R.id.reciclerViewProductsUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mapButton = view.findViewById(R.id.buttonMapFragment);

        authService = AuthService.getInstance();
        tokenManager = new TokenManager(getActivity());

        showMapFragmentButton();
        cargarProductosDeAgricultores();

        return view;
    }

    /**
     * Obtiene los productos de los agricultores en el rango y los muestra en pantalla.
     * @author Oriol
     */
    private void cargarProductosDeAgricultores() {
        String token = tokenManager.getToken();
        if (agricultoresIdsEnRango != null) {
            for (int id : agricultoresIdsEnRango) {
                authService.getAllProducts(id, token, new AuthService.AuthCallback<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> productos) {
                        productosEnRango.addAll(productos);
                        mostrarProductosEnPantalla();
                        mapButton.setEnabled(false);
                        mapButton.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e("UserProductFragment", "Error al obtener productos: " + error.getMessage());
                        Toast.makeText(getContext(), "Error al cargar productos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            showMapFragmentButton();
            Log.e("UserProductFragment", "La lista de IDs de agricultores está vacía");
            Toast.makeText(getContext(), "No hay agricultores en el rango", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Muestra los productos en la pantalla y gestiona la visibilidad del botón del MapFragment.
     * @author Oriol
     */
    private void mostrarProductosEnPantalla() {
        if (!productList.isEmpty()) {
            isMapButtonVisible = false;
            mapButton.setVisibility(View.GONE);
            mapButton.setEnabled(false);
        } else {
            isMapButtonVisible = true;
            mapButton.setVisibility(View.VISIBLE);
            mapButton.setEnabled(true);
        }
        adapter = new ProductAdapterUserLobby(productosEnRango, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Muestra el botón que abre el MapFragment y gestiona su funcionalidad.
     * @author Oriol
     */
    private void showMapFragmentButton() {
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapFragment();
            }
        });
    }

    /**
     * Abre el MapFragment.
     * @autor Oriol
     */
    private void openMapFragment() {
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerUserLobby, mapFragment);
        transaction.commit();
    }

    /**
     * Maneja el clic en un producto.
     * @author Ainoha
     * @param product El producto en el que se hizo clic.
     */
    @Override
    public void onProductClick(Product product) {
        Context context = getContext();
        int productId = product.getProductId();

        TokenManager tokenManager = TokenManager.getInstance(getContext());
        tokenManager.saveProductId(productId);
        authService.getUserProfile(product.getIdAgricultor(), new AuthService.ProfileCallback() {
            @Override
            public void onSuccess(User userProfile) {
                if (context != null) {
                    Intent intent = new Intent(context, AddToCartActivity.class);
                    intent.putExtra("nombre", product.getName());
                    intent.putExtra("categoriaId", product.getCategoriaId());
                    intent.putExtra("descripcion", product.getDescripcion());
                    intent.putExtra("precio", product.getPrecio());
                    intent.putExtra("tipoDePeso", product.getUnidadMedida());
                    intent.putExtra("productId", productId);
                    intent.putExtra("idAgricultor", product.getIdAgricultor());
                    intent.putExtra("vendedor", userProfile.getUsername());
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Error: No se puede abrir la actividad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("UserProductFragment", "Error al obtener el perfil del agricultor: " + t.getMessage());
            }
        });
    }
}
