package com.example.localmarket.model;

public class CartItem {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private String unidadMedida;
    private int categoriaId;
    private int agricultorId;



    public CartItem(int agricultorId,int productId,int categoriaId, String productName, double price, int quantity, String unidadMedida) {
        this.agricultorId= agricultorId;
        this.productId = productId;
        this.categoriaId=categoriaId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.unidadMedida=unidadMedida;

    }

    public int getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(int agricultorId) {
        this.agricultorId = agricultorId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }


}
