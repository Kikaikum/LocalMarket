package com.example.localmarket.model;

public class ProductRequest {
    private String nombre;
    private int categoriaId;
    private String descripcion;
    private String unidadMedida;
    private double precio;
    private double stock;
    private int idAgricultor;
    String token;


    // Constructor
    public ProductRequest(String nombre, int categoriaId, double precio, String unidadMedida, String descripcion, int idAgricultor, double stock, String token) {
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
        this.stock = stock;
        this.idAgricultor = idAgricultor;
        this.token = token;
    }

    // Getters y Setters
    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public void setUnidadMedida(String tipoDePeso) {
        this.unidadMedida = tipoDePeso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }
}
