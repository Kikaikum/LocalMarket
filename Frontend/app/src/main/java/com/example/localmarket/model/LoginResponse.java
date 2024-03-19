package com.example.localmarket.model;

public class LoginResponse {
    private boolean success;
    private String token;
    private String errorMessage;
    private boolean vendor;

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public boolean isVendor() { return vendor; }

    public void setVendor(boolean vendor) { this.vendor = vendor; }
}

