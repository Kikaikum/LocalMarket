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
import com.example.localmarket.model.Order;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.OrderAdapterSeller;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SellerOrderFragment extends Fragment implements OrderAdapterSeller.OnOrderClickListener {
    private AuthService authService;
    private TokenManager tokenManager;
    private OrderAdapterSeller adapter;
    private RecyclerView recyclerViewOrders;
    private List<Map<String, Integer>> orderList = new ArrayList<>();
    private int idAgricultor;

    public SellerOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService();
        tokenManager = new TokenManager(getActivity());
        idAgricultor = tokenManager.getUserId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_order, container, false);
        orderList=new ArrayList<>();
        adapter = new OrderAdapterSeller(orderList,this);

        recyclerViewOrders = view.findViewById(R.id.reciclerViewOrders);
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setAdapter(adapter);

        authService=AuthService.getInstance();
        tokenManager= new TokenManager(getActivity());

        fetchOrdersByFarmer(idAgricultor);
        return view;
    }

    private void fetchOrdersByFarmer(int farmerId) {
        authService.getAllOrdersByFarmer(farmerId, new AuthService.AuthCallback<List<Map<String, Integer>>>() {
            @Override
            public void onSuccess(List<Map<String, Integer>> orders) {
                orderList.clear(); // Limpiar la lista antes de agregar las nuevas órdenes
                orderList.addAll(orders);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                } else {
                    // Manejar el caso en el que el adaptador es null
                    Log.e("SellerProductFragment", "El adaptador es null, no se puede actualizar la UI");
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Error", "Error al obtener las órdenes: " + t.getMessage());
            }
        });
    }

    @Override
    public void onOrderClick(Order order) {
        //return order;
    }
}