package com.example.localmarket.model;

import java.util.Map;

public class Order {
    private int idCliente;
    private int idAgricultor;
    private Map<Integer, Integer> productos; // Mapa de idProducto -> cantidad
    private String estado;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public Map<Integer, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<Integer, Integer> productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}