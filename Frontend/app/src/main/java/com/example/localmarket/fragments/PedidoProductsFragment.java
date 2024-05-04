package com.example.localmarket.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.utils.ProductsOrderAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PedidoProductsFragment extends Fragment {
    private static final String ARG_PRODUCTOS = "productos";
    private static List<Product> productos;

    private ProductsOrderAdapter adapter;

    public PedidoProductsFragment() {
        // Required empty public constructor
    }


    public static PedidoProductsFragment newInstance(List<Product> productos) {
        PedidoProductsFragment fragment = new PedidoProductsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCTOS, (Serializable) productos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productos = (List<Product>) getArguments().getSerializable(ARG_PRODUCTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_products, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }
}