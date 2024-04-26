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
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.activities.AddToCartActivity;
import com.example.localmarket.activities.EditProductActivity;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class UserProductFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private AuthService authService;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager;


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
        // Configuración inicial

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_product, container, false);

        // Inicializa el RecyclerView y el adaptador
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, this);
        recyclerView = view.findViewById(R.id.reciclerViewProductsUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        authService = AuthService.getInstance();
        tokenManager = new TokenManager(getActivity());






        getAllProductsAvailable();

        return view;
    }

    /**
     * Obtiene todos los productos del vendedor desde el servidor.
     * @author Ainoha

     */
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