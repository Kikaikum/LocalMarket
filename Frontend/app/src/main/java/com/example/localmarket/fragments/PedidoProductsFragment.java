package com.example.localmarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductsOrderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoProductsFragment extends Fragment {
    private static final String ARG_PEDIDO_ITEMS = "pedidoItems"; // Constante para identificar el argumento del bundle.
    private RecyclerView recyclerView; // RecyclerView para mostrar los productos.
    private ProductsOrderAdapter adapter; // Adaptador para el RecyclerView.




    public PedidoProductsFragment() {
        // Constructor público vacío requerido.
    }

    // Método factory para crear una nueva instancia de este fragmento con parámetros específicos.
    public static PedidoProductsFragment newInstance(Bundle args) {
        PedidoProductsFragment fragment = new PedidoProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Map<String, Integer>> pedidoItems;
        // Infla el layout para este fragmento.
        View view = inflater.inflate(R.layout.fragment_pedido_products, container, false);

        // Configura el RecyclerView.
        recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pedidoItems = (List<Map<String, Integer>>) getArguments().getSerializable(ARG_PEDIDO_ITEMS);
        adapter = new ProductsOrderAdapter(pedidoItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view; // Devuelve la vista inflada y configurada.
    }

}
