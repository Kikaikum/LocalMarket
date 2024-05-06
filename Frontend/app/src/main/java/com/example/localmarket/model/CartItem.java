package com.example.localmarket.model;

public class CartItem {
    private int itemId;
    private String productName;
    private double price;
    private int cantidad;
    private String unidadMedida;
    private int categoriaId;
    private int agricultorId;



    public CartItem(int agricultorId,int itemId,int categoriaId, String productName, double price, int cantidad, String unidadMedida) {
        this.agricultorId= agricultorId;
        this.itemId = itemId;
        this.categoriaId=categoriaId;
        this.productName = productName;
        this.price = price;
        this.cantidad = cantidad;
        this.unidadMedida=unidadMedida;

    }
    public CartItem(){};

    public int getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(int agricultorId) {
        this.agricultorId = agricultorId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int productId) {
        this.itemId = itemId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }


}
