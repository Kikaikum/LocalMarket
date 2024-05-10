/**
 * Fragmento que muestra los pedidos del agricultor.
 * Permite al agricultor ver los pedidos que ha recibido.
 *
 * @author Oriol Estero Sanchez
 */
package com.example.localmarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Order;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.OrderAdapterSeller;
import com.example.localmarket.utils.TokenManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SellerOrderFragment extends Fragment implements OrderAdapterSeller.OnOrderClickListener {
    protected AuthService authService;
    protected TokenManager tokenManager;
    protected OrderAdapterSeller adapter;
    private RecyclerView recyclerViewOrders;
    protected List<Order> orderList = new ArrayList<>();
    private int idAgricultor;
    Bundle bundle;
    private FragmentManager fragmentManager;

    /**
     * Constructor vacío.
     */
    public SellerOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        authService = new AuthService();
        tokenManager = new TokenManager(getActivity());
        idAgricultor = tokenManager.getUserId();
        adapter = new OrderAdapterSeller(orderList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_order, container, false);

        recyclerViewOrders = view.findViewById(R.id.reciclerViewOrders);
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setAdapter(adapter);

        tokenManager = new TokenManager(getActivity());

        fetchOrdersByFarmer(idAgricultor);
        return view;
    }

    /**
     * Método para obtener los pedidos del agricultor desde el servidor.
     *
     * @param farmerId ID del agricultor.
     */
    protected void fetchOrdersByFarmer(int farmerId) {
        String token = tokenManager.getToken();
        authService.getAllOrdersByFarmer(farmerId, token, new AuthService.AuthCallback<List<Order>>() {
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


    /**
     * Método invocado al hacer clic en un pedido.
     *
     * @param order Pedido seleccionado.
     */
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
        }
    }


    /**
     * Método para obtener la lista de pedidos.
     *
     * @return Lista de pedidos.
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * Método para establecer el FragmentManager.
     *
     * @param fragmentManager FragmentManager a establecer.
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


}