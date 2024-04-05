package com.example.localmarket.model;

public class ProductResponse {
    private String id; // ID único del producto
    private String message; // Mensaje opcional del servidor

    // Constructor, getters y setters
    public ProductResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
