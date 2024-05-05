package com.example.localmarket.fragments;

import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SellerOrderFragment extends Fragment implements OrderAdapterSeller.OnOrderClickListener {
    private AuthService authService;
    private TokenManager tokenManager;
    private OrderAdapterSeller adapter;
    private RecyclerView recyclerViewOrders;
    private List<Order> orderList = new ArrayList<>();
    private int idAgricultor;
    Bundle bundle;

    public SellerOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=new Bundle();
        authService = new AuthService();
        tokenManager = new TokenManager(getActivity());
        idAgricultor = tokenManager.getUserId();
        adapter = new OrderAdapterSeller(orderList,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_order, container, false);

        recyclerViewOrders = view.findViewById(R.id.reciclerViewOrders);
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setAdapter(adapter);

        tokenManager= new TokenManager(getActivity());

        fetchOrdersByFarmer(idAgricultor);
        return view;
    }

    private void fetchOrdersByFarmer(int farmerId) {
        authService.getAllOrdersByFarmer(farmerId, new AuthService.AuthCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> orders) {
                List<Order> farmerOrders = new ArrayList<>();
                for (Order order : orders) {
                    if (order.getIdAgricultor() == farmerId) {
                        farmerOrders.add(order);
                    }
                }
                orderList.clear();
                orderList.addAll(farmerOrders);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Error", "Error al obtener las órdenes: " + t.getMessage());
            }
        });
    }

    @Override
    public void onOrderClick(Order order) {
        if (order != null && order.getPedido() != null) {
            List<Map<String, Integer>> pedidoItems = order.getPedido(); // Obtiene los ítems del pedido
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", (Serializable) order);
            bundle.putSerializable("pedidoItems", (Serializable) pedidoItems); // Asegúrate de que Map<String, Integer> sea serializable
            Log.e("OrderClick", "Pedido Items: " + pedidoItems.toString());

            PedidoProductsFragment productsFragment = PedidoProductsFragment.newInstance(bundle);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container2, productsFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.e("SellerOrderFragment", "Order object or pedido list is null");
        }
    }
}