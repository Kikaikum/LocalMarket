package com.example.localmarket.model;

public class ProductRequest {
    private String name;
    private int imageId;
    private String descripcion;
    private String tipoDePeso;
    private double precio;

    // Constructor
    public ProductRequest(String name,int imageId, String descripcion, String tipoDePeso, double precio) {
        this.name = name;
        this.imageId = imageId;
        this.descripcion = descripcion;
        this.tipoDePeso = tipoDePeso;
        this.precio = precio;
    }

    // Getters y Setters
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
}
