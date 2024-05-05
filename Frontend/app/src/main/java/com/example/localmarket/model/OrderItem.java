package com.example.localmarket.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int itemId;
    private int cantidad;

    // Constructor sin argumentos
    public OrderItem() {
    }

    // Constructor para inicialización rápida
    public OrderItem(int itemId, int cantidad) {
        this.itemId = itemId;
        this.cantidad = cantidad;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
