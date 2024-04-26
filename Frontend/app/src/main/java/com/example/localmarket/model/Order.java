package com.example.localmarket.model;

import java.util.Map;

public class Order {
    private int idCliente;
    private Map<Integer, ProductInfo> productos;
    private String estado;

    // Getters y setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Map<Integer, ProductInfo> getProductos() {
        return productos;
    }

    public void setProductos(Map<Integer, ProductInfo> productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}