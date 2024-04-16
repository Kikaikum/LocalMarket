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
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerProductFragment extends Fragment implements ProductAdapter.OnProductClickListener, AddProductFragment.OnProductAddedListener{

    private AuthService authService;
    private Button btnAdd;
    private List<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager;

    public SellerProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerProfuctFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerProductFragment newInstance(String param1, String param2) {
        SellerProductFragment fragment = new SellerProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuración del RecyclerView

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_product, container, false);

        // Inicializa el RecyclerView y el adaptador
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, this);
        recyclerView = view.findViewById(R.id.reciclerViewProducts);
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

        // Obtener todos los productos del agricultor
        int userId = tokenManager.getUserId();
        String token = tokenManager.getToken();
        getAllProducts(userId, token);

        return view;
    }

    private void getAllProducts(int userId, String token) {
        authService.getAllProducts(userId, token,  new AuthService.AuthCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                // Limpiar la lista actual de productos y agregar los productos obtenidos
                productList.clear();
                productList.addAll(products);
                // Notificar al adaptador sobre el cambio en los datos
                adapter.notifyDataSetChanged();
                // Obtener el primer producto de la lista y guardar su ID en el TokenManager
                if (!products.isEmpty()) {
                    int firstProductId = products.get(0).getProductId(); // Asumiendo que el ID del producto está en un atributo llamado "id"
                    tokenManager.saveProductId(firstProductId);
                }
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores, como mostrar un mensaje de error al usuario
                Toast.makeText(getContext(), "Error al obtener productos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductClick(Product product) {
        // Obtiene el contexto del Fragmento
        Context context = getContext();
        // Obtén el ID del producto seleccionado
        int productId = product.getProductId(); // Asumiendo que hay un método getId() en la clase Product para obtener el ID

        // Obtén una instancia de TokenManager
        TokenManager tokenManager = TokenManager.getInstance(getContext());

        // Guarda el ID del producto seleccionado utilizando el método saveProductId()
        tokenManager.saveProductId(productId);


        if (context != null) {



            // Crea un Intent para abrir EditProductActivity
            Intent intent = new Intent(context, EditProductActivity.class);

            // Pasa los datos del producto seleccionado a EditProductActivity
            intent.putExtra("nombre", product.getName());
            intent.putExtra("categoriaId", product.getCategoriaId());
            intent.putExtra("descripcion", product.getDescripcion());
            intent.putExtra("tipoDePeso", product.getUnidadMedida());
            intent.putExtra("precio", product.getPrecio());
            intent.putExtra("stock", product.getStock());

            // Inicia la actividad
            startActivity(intent);
        } else {
            // Maneja el caso donde el contexto es nulo
            Toast.makeText(getContext(), "Error: No se puede abrir la actividad", Toast.LENGTH_SHORT).show();
        }
    }

    public void addProductToList(Product product) {
        // Agregar el nuevo producto a la lista y notificar al adaptador
        productList.add(product);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProductAdded(Product product) {
        addProductToList(product);
    }
}