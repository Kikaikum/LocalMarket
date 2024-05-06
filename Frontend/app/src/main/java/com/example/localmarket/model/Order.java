package com.example.localmarket.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
   private Integer id;
    private int clientId;
    private int agricultorId;
    private List<Map<String, Integer>> pedido;
    private String estado;
    private String createdAt;
    public Order(int clientId, int agricultorId, List<Map<String, Integer>> pedido, String estado) {
        this.clientId = clientId;
        this.agricultorId = agricultorId;
        this.pedido = pedido;
        this.estado = estado;

    }
    public Order() {
        // Constructor vacío
    }


    // Getters y setters
    public int getId() {
        return id;
    }

   public void setId(Integer id) {
       this.id = id;
   }

    public int getIdCliente() {
        return clientId;
    }

    public void setIdCliente(int clientId) {
        this.clientId = clientId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Método para añadir ítems al pedido
    public void addPedidoItem(int itemId, int cantidad) {
        Map<String, Integer> newItem = new HashMap<>();
        newItem.put("itemId", itemId);
        newItem.put("cantidad", cantidad);
        this.pedido.add(newItem);
    }
}
