package com.example.localmarket.model;

public class ProductResponse {
    private String id; // ID único del producto
    private String nombre; // Nombre del producto
    private int categoriaId; // ID de la categoría del producto
    private double precio; // Precio del producto
    private String unidadMedida; // Unidad de medida del producto
    private String descripcion; // Descripción del producto
    private String idAgricultor; // ID del agricultor asociado al producto
    private double stock; // Stock disponible del producto

    // Constructor, getters y setters
    public ProductResponse(String id, String nombre, int categoriaId, double precio, String unidadMedida, String descripcion, String idAgricultor, double stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.descripcion = descripcion;
        this.idAgricultor = idAgricultor;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(String idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}

