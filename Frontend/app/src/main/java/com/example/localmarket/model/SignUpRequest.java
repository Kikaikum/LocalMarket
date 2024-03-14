package com.example.localmarket.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private String username;
    private String agricultor;

    public SignUpRequest(String email, String password, String userName, String name, String surname, String isVendor) {
        this.email = email;
        this.password = password;
        this.nombre = name;
        this.agricultor=isVendor;
        this.username= userName;
        this.apellidos = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public String getSurname() { return apellidos; }

    public void setSurname(String surname) { this.apellidos = surname; }

    public String getUserName() { return username; }

    public void setUserName(String userName) { this.username = userName; }

    public String getVendor() { return agricultor; }

    public void setVendor(String vendor) { agricultor = vendor; }

}