package com.example.localmarket.model;

/**
 * Clase que representa un elemento del carrito de compras.
 * @author Ainoha
 */
public class CartItem {
    private int itemId;
    private String productName;
    private double price;
    private int cantidad;
    private String unidadMedida;
    private int categoriaId;
    private int agricultorId;

    /**
     * Constructor de la clase CartItem.
     *
     * @param agricultorId  El ID del agricultor.
     * @param itemId        El ID del elemento (producto) en el carrito.
     * @param categoriaId   El ID de la categoría del producto.
     * @param productName   El nombre del producto.
     * @param price         El precio del producto.
     * @param cantidad      La cantidad del producto.
     * @param unidadMedida  La unidad de medida del producto.
     */
    public CartItem(int agricultorId, int itemId, int categoriaId, String productName, double price, int cantidad, String unidadMedida) {
        this.agricultorId = agricultorId;
        this.itemId = itemId;
        this.categoriaId = categoriaId;
        this.productName = productName;
        this.price = price;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

    /**
     * Constructor vacío de la clase CartItem.
     */
    public CartItem() {
    }

    // Getters y setters

    /**
     * Obtiene el ID del agricultor.
     *
     * @return El ID del agricultor.
     */
    public int getAgricultorId() {
        return agricultorId;
    }

    /**
     * Establece el ID del agricultor.
     *
     * @param agricultorId El ID del agricultor a establecer.
     */
    public void setAgricultorId(int agricultorId) {
        this.agricultorId = agricultorId;
    }

    /**
     * Obtiene el ID del elemento (producto) en el carrito.
     *
     * @return El ID del elemento (producto) en el carrito.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Establece el ID del elemento (producto) en el carrito.
     *
     * @param itemId El ID del elemento (producto) en el carrito a establecer.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Obtiene el ID de la categoría del producto.
     *
     * @return El ID de la categoría del producto.
     */
    public int getCategoriaId() {
        return categoriaId;
    }

    /**
     * Establece el ID de la categoría del producto.
     *
     * @param categoriaId El ID de la categoría del producto a establecer.
     */
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param productName El nombre del producto a establecer.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece el precio del producto.
     *
     * @param price El precio del producto a establecer.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Obtiene la cantidad del producto.
     *
     * @return La cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto.
     *
     * @param cantidad La cantidad del producto a establecer.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la unidad de medida del producto.
     *
     * @return La unidad de medida del producto.
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Establece la unidad de medida del producto.
     *
     * @param unidadMedida La unidad de medida del producto a establecer.
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
