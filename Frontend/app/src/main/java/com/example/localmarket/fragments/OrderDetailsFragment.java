package com.example.localmarket.fragments;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.activities.ActivityUserLobby;
import com.example.localmarket.activities.OrderActivity;
import com.example.localmarket.model.CartItem;
import com.example.localmarket.model.Order;

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
    private Button btnVolver;
    private TokenManager tokenManager;
    private int numAgricultores;

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


        tokenManager=TokenManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetails_fragment, container, false);
        Toolbar toolbar = ((OrderActivity) requireActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

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

        btnVolver = view.findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
                ;
            }
        });

        // Inicializa el botón "Enviar pedido"
        buttonEnviarPedido = view.findViewById(R.id.buttonEnviarPedido);
        buttonEnviarPedido.setOnClickListener(v -> enviarPedidoAlServidor(orderManager.getCartItems()));

        return view;
    }

    private Map<Integer, List<CartItem>> groupProductsByAgricultor(List<CartItem> cartItems) {
        Map<Integer, List<CartItem>> groupedProducts = new HashMap<>();

        for (CartItem item : cartItems) {
            int idAgricultor = item.getAgricultorId();
            if (!groupedProducts.containsKey(idAgricultor)) {
                groupedProducts.put(idAgricultor, new ArrayList<>());
            }
            groupedProducts.get(idAgricultor).add(item);
        }

        return groupedProducts;
    }

    private void calculateAndDisplayTotalAmount(List<CartItem> cartItems) {
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice() * item.getCantidad();
        }
        textViewTotalAmount.setText(String.format("%.2f", totalAmount));
    }


    private void enviarPedidoAlServidor(List<CartItem> cartItems) {
        Map<Integer, List<CartItem>> groupedProducts = groupProductsByAgricultor(cartItems);
        numAgricultores = groupedProducts.size(); // Obtener el número de agricultores

        for (Map.Entry<Integer, List<CartItem>> entry : groupedProducts.entrySet()) {
            int clientId = tokenManager.getUserId(); // Valor predeterminado en caso de que no se pueda obtener el ID del usuario
            int idAgricultor = entry.getKey();
            List<CartItem> productsForAgricultor = entry.getValue();

            // Verificar si el usuario es un agricultor antes de obtener su ID
            if (tokenManager.getUser() != null && !tokenManager.getUser().getAgricultor()) {
                clientId = tokenManager.getUser().getId();
            } else {
                // Manejar el caso en el que el usuario es un agricultor
                // Podrías mostrar un mensaje de error o tomar otra acción apropiada
                Log.e("OrderDetailsFragment", "El usuario es un agricultor, no se puede obtener el ID del cliente");
            }

            // Crear logs para mostrar los parámetros antes de enviar el pedido al servidor
            Log.d("OrderDetailsFragment", "ID Cliente: " + clientId);
            Log.d("OrderDetailsFragment", "ID Agricultor: " + idAgricultor);
            Log.d("OrderDetailsFragment", "Productos para Agricultor: " + productsForAgricultor.toString());
            Order order = createOrder(clientId, idAgricultor, productsForAgricultor);
            sendOrderToServer(order);
        }
    }
    private Order createOrder(int clientId, int idAgricultor, List<CartItem> products) {
        Order order = new Order();
        order.setIdCliente(clientId);
        order.setIdAgricultor(idAgricultor);

        List<Map<String, Integer>> productList = new ArrayList<>();
        for (CartItem item : products) {
            Map<String, Integer> product = new HashMap<>();
            product.put("itemId", item.getItemId());
            product.put("cantidad", item.getCantidad());
            productList.add(product);
        }
        order.setPedido(productList);
        order.setEstado("creado");

        return order;
    }

    private void sendOrderToServer(Order order) {
        AuthService authService = new AuthService();
        authService.sendOrder(order, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                // Manejar el éxito del envío del pedido
                Toast.makeText(getContext(), "Has realizado "+ numAgricultores +" pedidos exitosamente", Toast.LENGTH_SHORT).show();
                // Vaciar la cesta de compras
                orderManager.clearCartItems();
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error al enviar el pedido
                Toast.makeText(getContext(), "Error al enviar el pedido: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLobbyUser() {
        Intent intent = new Intent(getContext(), ActivityUserLobby.class);
        startActivity(intent);
        // Opcionalmente, puedes agregar la siguiente línea para cerrar el OrderDetailsFragment
        requireActivity().finish();
    }

}