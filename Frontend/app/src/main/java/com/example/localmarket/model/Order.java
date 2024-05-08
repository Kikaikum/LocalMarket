package com.example.localmarket.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa un pedido de productos.
 * @author Oriol + Ainoha
 */
public class Order implements Serializable {
    private Integer id;
    private int clientId;
    private int agricultorId;
    private List<Map<String, Integer>> pedido;
    private String estado;
    private String createdAt;

    /**
     * Constructor de la clase Order.
     *
     * @param clientId     El ID del cliente que realiza el pedido.
     * @param agricultorId El ID del agricultor al que se realiza el pedido.
     * @param pedido       La lista de ítems del pedido, representados como un mapa de ID de producto y cantidad.
     * @param estado       El estado del pedido.
     */
    public Order(int clientId, int agricultorId, List<Map<String, Integer>> pedido, String estado) {
        this.clientId = clientId;
        this.agricultorId = agricultorId;
        this.pedido = pedido;
        this.estado = estado;
    }

    /**
     * Constructor vacío de la clase Order.
     */
    public Order() {
        // Constructor vacío
    }

    // Getters y setters

    /**
     * Obtiene el ID del pedido.
     *
     * @return El ID del pedido.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del pedido.
     *
     * @param id El ID del pedido a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del cliente que realizó el pedido.
     *
     * @return El ID del cliente.
     */
    public int getIdCliente() {
        return clientId;
    }

    /**
     * Establece el ID del cliente que realizó el pedido.
     *
     * @param clientId El ID del cliente a establecer.
     */
    public void setIdCliente(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Obtiene el ID del agricultor al que se realiza el pedido.
     *
     * @return El ID del agricultor.
     */
    public int getIdAgricultor() {
        return agricultorId;
    }

    /**
     * Establece el ID del agricultor al que se realiza el pedido.
     *
     * @param agricultorId El ID del agricultor a establecer.
     */
    public void setIdAgricultor(int agricultorId) {
        this.agricultorId = agricultorId;
    }

    /**
     * Obtiene la lista de ítems del pedido.
     *
     * @return La lista de ítems del pedido.
     */
    public List<Map<String, Integer>> getPedido() {
        return pedido;
    }

    /**
     * Establece la lista de ítems del pedido.
     *
     * @param pedido La lista de ítems del pedido a establecer.
     */
    public void setPedido(List<Map<String, Integer>> pedido) {
        this.pedido = pedido;
    }

    /**
     * Obtiene el estado del pedido.
     *
     * @return El estado del pedido.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     *
     * @param estado El estado del pedido a establecer.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha de creación del pedido.
     *
     * @return La fecha de creación del pedido.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Establece la fecha de creación del pedido.
     *
     * @param createdAt La fecha de creación del pedido a establecer.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Métodos

    /**
     * Método para añadir un nuevo ítem al pedido.
     *
     * @param itemId   El ID del producto a añadir.
     * @param cantidad La cantidad del producto a añadir.
     */
    public void addPedidoItem(int itemId, int cantidad) {
        Map<String, Integer> newItem = new HashMap<>();
        newItem.put("itemId", itemId);
        newItem.put("cantidad", cantidad);
        this.pedido.add(newItem);
    }
}
