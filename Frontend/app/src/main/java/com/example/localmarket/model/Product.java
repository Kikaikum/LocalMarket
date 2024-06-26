package com.example.localmarket.model;

/**
 * Clase que representa un producto en el mercado local.
 * Contiene detalles como el ID de categoría, nombre, descripción, tipo de peso, precio y ID de producto.
 *
 * @author Oriol Estero Sanchez
 */
public class Product {
    private int categoriaId;
    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private double precio;
    private int id;
    private double stock;
    private int idAgricultor;

    /**
     * Constructor de la clase Product para un producto sin ID.
     *
     * @param imageId ID de la categoría del producto.
     * @param nombre   Nombre del producto.
     */
    public Product(int imageId, String nombre) {
        this.categoriaId = imageId;
        this.nombre=nombre;
    }

    /**
     * Constructor de la clase Product para un producto con todos los detalles.
     *
     * @param categoriaId     ID de la categoría del producto.
     * @param nombre         Nombre del producto.
     * @param descripcion  Descripción del producto.
     * @param unidadMedida   Tipo de peso del producto.
     * @param precio       Precio del producto.
     * @param stock        Cantidad de producto
     */
    public Product(String nombre, int categoriaId, double precio, String descripcion, String unidadMedida, double stock) {

        this.categoriaId = categoriaId;
        this.nombre=nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Obtiene el ID de categoría del producto.
     *
     * @return El ID de categoría.
     */
    public int getCategoriaId() {
        return categoriaId;
    }

    /**
     * Establece el ID de categoría del producto.
     *
     * @param categoriaId El ID de categoría a establecer.
     */
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getName() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nombre del producto a establecer.
     */
    public void setName(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion La descripción del producto a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El precio del producto a establecer.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el ID del producto.
     *
     * @return El ID del producto.
     */
    public int getProductId() {
        return id;
    }

    /**
     * Establece el ID del producto.
     *
     * @param id El ID del producto a establecer.
     */
    public void setProductId(int id) {
        this.id = id;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public int getIdAgricultor(){return idAgricultor;}


}
