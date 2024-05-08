package com.example.localmarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Order;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.ProductsOrderAdapter;
import com.example.localmarket.utils.TokenManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoProductsFragment extends Fragment {
    private static final String ARG_PEDIDO_ITEMS = "pedidoItems"; // Constante para identificar el argumento del bundle.
    private RecyclerView recyclerView; // RecyclerView para mostrar los productos.
    private ProductsOrderAdapter adapter; // Adaptador para el RecyclerView.
    private Button butonAcceptOrder;
    private List<Map<String, Integer>> pedidoItems;
    private AuthService authService;

    private String token;


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
        authService = new AuthService();
        TokenManager tokenManager = new TokenManager(getActivity());
        token = tokenManager.getToken();

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
        // Devuelve la vista inflada y configurada.

        butonAcceptOrder = view.findViewById(R.id.btnAcceptOrder);
        butonAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderStatus(getArguments());
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void changeOrderStatus(Bundle args) {
        Order order = (Order) args.getSerializable("order");
        if (order != null) {
            // Configurar el nuevo estado del pedido
            order.setEstado("aceptado");

            // Preparar los datos para la API
            Map<String, String> statusUpdate = new HashMap<>();
            statusUpdate.put("estado", order.getEstado());

            // Realizar la llamada a la API para actualizar el estado

            authService.updateOrderStatus(order.getId(), token, statusUpdate, new AuthService.AuthCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Log.d("PedidoProductsFragment", "Estado del pedido actualizado exitosamente.");
                    // Puedes actualizar la UI aquí si es necesario
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("PedidoProductsFragment", "Error al actualizar el estado del pedido: " + t.getMessage());
                    // Manejo de error, por ejemplo mostrar un mensaje al usuario
                }

            });
        } else {
            Log.e("PedidoProductsFragment", "El objeto order es nulo");
        }
    }

}
