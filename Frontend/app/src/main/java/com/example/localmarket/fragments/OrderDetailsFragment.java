package com.example.localmarket.fragments;

import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.CartItem;
import com.example.localmarket.model.Order;
import com.example.localmarket.model.ProductInfo;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.OrderAdapter;
import com.example.localmarket.utils.OrderManager;
import com.example.localmarket.utils.TokenManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailsFragment extends Fragment {
    private List<CartItem> cartItemList;
    private OrderAdapter adapter;
    private RecyclerView recyclerView;
    private OrderManager orderManager;
    private TextView textViewTotalAmount;
    private Button buttonEnviarPedido;
    private TokenManager tokenManager;

    public OrderDetailsFragment() {
        // Constructor público requerido
    }

    public static OrderDetailsFragment newInstance() {
        return new OrderDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicialización de la lista de elementos del carrito
        cartItemList = new ArrayList<>();
        // Inicializa el adaptador
        adapter = new OrderAdapter(cartItemList);
        orderManager = OrderManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetails_fragment, container, false);

        // Inicializa el RecyclerView
        recyclerView = view.findViewById(R.id.reciclerViewOrderedProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);
        orderManager = OrderManager.getInstance(getContext());

        // Obtener la lista de elementos del carrito desde OrderManager
        ArrayList<CartItem> cartItems = orderManager.getCartItems();

        // Inicializar el adaptador con la lista de elementos del carrito
        adapter = new OrderAdapter(cartItems);
        recyclerView.setAdapter(adapter);

        // Calcula y muestra el importe total
        calculateAndDisplayTotalAmount(cartItems);

        // Establece un listener para detectar cambios en los elementos del carrito
        adapter.setOnCartItemChangedListener(() -> {
            // Obtiene la lista actualizada de elementos del carrito
            ArrayList<CartItem> updatedCartItems = adapter.getCartItemList();

            // Recalcula y muestra el importe total
            calculateAndDisplayTotalAmount(updatedCartItems);

            // Guarda los cambios en el OrderManager
            orderManager.saveCartItem(updatedCartItems);
        });

        // Inicializa el botón "Enviar pedido"
        buttonEnviarPedido = view.findViewById(R.id.buttonEnviarPedido);
        buttonEnviarPedido.setOnClickListener(v -> enviarPedido());

        return view;
    }

    private void calculateAndDisplayTotalAmount(List<CartItem> cartItems) {
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        textViewTotalAmount.setText(String.format("%.2f", totalAmount));
    }

    private void enviarPedido() {
        // Crear el objeto Order con los datos necesarios
        Order order = new Order();
        order.setIdCliente(tokenManager.getUserId());


        // Crear el mapa de productos
        Map<Integer, ProductInfo> productos = new HashMap<>();
        for (CartItem item : cartItemList) {
            productos.put(item.getProductId(), new ProductInfo(item.getAgricultorId(), item.getQuantity()));
        }
        order.setProductos(productos);

        order.setEstado("creado");

        // Enviar el pedido al servidor
        enviarPedidoAlServidor(order);
    }

    private void enviarPedidoAlServidor(Order order) {
        int idCliente = tokenManager.getUserId(); // Obtener el ID del cliente desde el TokenManager

        AuthService authService = new AuthService(); // Instanciar AuthService
        authService.sendOrder(idCliente, order, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                // Manejar el éxito del envío del pedido
                Toast.makeText(getContext(), "Pedido enviado exitosamente", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error al enviar el pedido
                Toast.makeText(getContext(), "Error al enviar el pedido: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}