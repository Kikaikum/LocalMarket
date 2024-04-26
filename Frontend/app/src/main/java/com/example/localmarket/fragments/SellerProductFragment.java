package com.example.localmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.activities.EditProductActivity;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductAdapter;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para mostrar los productos del vendedor y permitir agregar nuevos productos.
 * @author Ainoha + Oriol
 */
public class SellerProductFragment extends Fragment implements ProductAdapter.OnProductClickListener, AddProductFragment.OnProductAddedListener{

    private AuthService authService;
    private Button btnAdd;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager;

    public SellerProductFragment() {
        // Constructor público requerido
    }

    /**
     * Crea una nueva instancia de SellerProductFragment.
     *
     * @return Una nueva instancia de SellerProductFragment.
     */
    public static SellerProductFragment newInstance() {
        return new SellerProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuración inicial
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_product, container, false);

        // Inicializa el RecyclerView y el adaptador
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, this);

        recyclerView = view.findViewById(R.id.reciclerViewProductsSeller);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        authService = AuthService.getInstance();
        tokenManager = new TokenManager(getActivity());

        btnAdd = view.findViewById(R.id.addProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container2, new AddProductFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Obtener todos los productos del vendedor
        int userId = tokenManager.getUserId();
        String token = tokenManager.getToken();
        getAllProducts(userId, token);

        return view;
    }

    /**
     * Obtiene todos los productos del vendedor desde el servidor.
     * @author Ainoha
     * @param userId El ID del usuario/vendedor.
     * @param token  El token de autenticación.
     */
    public void getAllProducts(int userId, String token) {
        authService.getAllProducts(userId, token,  new AuthService.AuthCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                // Actualizar la lista de productos con los obtenidos del servidor
                productList.clear();
                productList.addAll(products);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                } else {
                    // Manejar el caso en el que el adaptador es null
                    Log.e("SellerProductFragment", "El adaptador es null, no se puede actualizar la UI");
                }
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
     * @author Ainoha
     * @param product El producto en el que se hizo clic.
     */
    @Override
    public void onProductClick(Product product) {
        // Manejar el clic en un producto para abrir la actividad de edición
        Context context = getContext();
        int productId = product.getProductId();

        TokenManager tokenManager = TokenManager.getInstance(getContext());
        tokenManager.saveProductId(productId);

        if (context != null) {
            Intent intent = new Intent(context, EditProductActivity.class);
            intent.putExtra("nombre", product.getName());
            intent.putExtra("categoriaId", product.getCategoriaId());
            intent.putExtra("descripcion", product.getDescripcion());
            intent.putExtra("tipoDePeso", product.getUnidadMedida());
            intent.putExtra("precio", product.getPrecio());
            intent.putExtra("stock", product.getStock());
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Error: No se puede abrir la actividad", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Agrega un producto a la lista de productos y notifica al adaptador.
     * @author Ainoha
     * @param product El producto a agregar.
     */
    public void addProductToList(Product product) {
        productList.add(product);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProductAdded(Product product) {
        // Método llamado cuando se agrega un producto
        addProductToList(product);
    }
}
