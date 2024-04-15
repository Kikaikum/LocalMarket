package com.example.localmarket.model;

/**
 * Clase que representa un producto en el mercado local.
 * Contiene detalles como el ID de categoría, nombre, descripción, tipo de peso, precio y ID de producto.
 *
 * @author Oriol Estero Sanchez
 */
public class Product {
    private int categoriaId;
    private String name;
    private String descripcion;
    private String unidadMedida;
    private double precio;
    private int productId;
    private double stock;

    /**
     * Constructor de la clase Product para un producto sin ID.
     *
     * @param imageId ID de la categoría del producto.
     * @param name     Nombre del producto.
     */
    public Product(int imageId, String name) {
        this.categoriaId = imageId;
        this.name = name;
    }

    /**
     * Constructor de la clase Product para un producto con todos los detalles.
     *
     * @param categoriaId     ID de la categoría del producto.
     * @param name         Nombre del producto.
     * @param descripcion  Descripción del producto.
     * @param unidadMedida   Tipo de peso del producto.
     * @param precio       Precio del producto.
     * @param stock        Cantidad de producto
     */
    public Product(String name, int categoriaId, double precio, String descripcion, String unidadMedida, double stock ) {
        this.productId = productId;
        this.categoriaId = categoriaId;
        this.name = name;
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
        return name;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param name El nombre del producto a establecer.
     */
    public void setName(String name) {
        this.name = name;
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
        return productId;
    }

    /**
     * Establece el ID del producto.
     *
     * @param productId El ID del producto a establecer.
     */
    public void setProductId(int productId) {
        this.productId = productId;
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
}
