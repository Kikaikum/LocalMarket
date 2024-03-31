package com.example.localmarket.model;

public class Product {
    private int imageId;
    private String name;
    private String descripcion;
    private String tipoDePeso;
    private double precio;
    private int id;



    public Product(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public Product(int imageId, String name, String descripcion, String tipoDePeso, double precio) {
        this.imageId = imageId;
        this.name = name;
        this.descripcion = descripcion;
        this.tipoDePeso = tipoDePeso;
        this.precio = precio;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
    public int getId() {
        return id;
    }
}
