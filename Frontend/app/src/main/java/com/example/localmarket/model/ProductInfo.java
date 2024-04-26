package com.example.localmarket.model;

public class ProductInfo {
    private int agricultorId;
    private int cantidad;

    public ProductInfo(int agricultorId, int cantidad) {
        this.agricultorId = agricultorId;
        this.cantidad = cantidad;
    }

    public int getAgricultorId() {
        return agricultorId;
    }

    public int getCantidad() {
        return cantidad;
    }
}