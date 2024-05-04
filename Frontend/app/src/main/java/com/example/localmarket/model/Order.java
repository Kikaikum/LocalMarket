package com.example.localmarket.model;

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
        return pedido.stream()
                .map(orderItem -> new Product(orderItem.getItemId(), orderItem.getCantidad()))
                .collect(Collectors.toList());
    }
}
