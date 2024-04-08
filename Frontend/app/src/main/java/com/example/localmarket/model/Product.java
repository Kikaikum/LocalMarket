package com.example.localmarket.model;

public class Product {
    private int categoriaId;
    private String name;
    private String descripcion;
    private String tipoDePeso;
    private double precio;
    private int productId;



    public Product(int imageId, String name) {
        this.categoriaId = imageId;
        this.name = name;
    }

    public Product(int productId,int imageId, String name, String descripcion, String tipoDePeso, double precio) {
        this.productId=productId;
        this.categoriaId = imageId;
        this.name = name;
        this.descripcion = descripcion;
        this.tipoDePeso = tipoDePeso;
        this.precio = precio;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDePeso() {
        return tipoDePeso;
    }

    public void setTipoDePeso(String tipoDePeso) {
        this.tipoDePeso = tipoDePeso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
