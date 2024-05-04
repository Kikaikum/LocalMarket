package com.example.localmarket.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {
    private int clientId;
    private int agricultorId;
    private List<Map<String, Integer>> pedido; // Lista de objetos con las claves "productId" y "quantity"
    private String estado;
    private List<OrderItem> comandas;

    public int getIdCliente() {
        return clientId;
    }

    public List<OrderItem> getComandas() {
        return comandas;
    }

    public void setComandas(List<OrderItem> comandas) {
        this.comandas = comandas;
    }

    public void setIdCliente(int idCliente) {
        this.clientId = idCliente;
    }

    public int getIdAgricultor() {
        return agricultorId;
    }

    public void setIdAgricultor(int agricultorId) {
        this.agricultorId = agricultorId;
    }

    public List<Map<String, Integer>> getPedido() {
        return pedido;
    }

    public void setPedido(List<Map<String, Integer>> pedido) {
        this.pedido = pedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public List<Product> getProductos() {
        List<Product> productos = new ArrayList<>();
        for (Map<String, Integer> orderItem : pedido) {
            Integer productIdInteger = orderItem.get("productId");
            Integer cantidadInteger = orderItem.get("cantidad");

            if (productIdInteger != null && cantidadInteger != null) {
                int productId = productIdInteger.intValue();
                String cantidad = String.valueOf(cantidadInteger.intValue());
                Product product = new Product(productId, cantidad);
                productos.add(product);
            } else {
                // Manejar el caso donde los valores son nulos
                Log.e("Order", "Valores nulos en el Map");
            }
        }
        return productos;
    }
}
