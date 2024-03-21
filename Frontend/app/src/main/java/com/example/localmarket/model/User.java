package com.example.localmarket.model;

public class User {

    private String nombre;
    private String apellidos;
    private String username;
    private String email;
    private String password;
    private int id;
    private Boolean vendor;

    // Constructor
    public User(String nombre, String apellidos, String username, String email, String password, int id, Boolean vendor) {
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.vendor= vendor;
    }

    // Getters y setters

    public String getName(){
        return nombre;
    }
    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getSurname(){
        return apellidos;
    }
    public void setSurname(String apellidos) {
        this.apellidos  = apellidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getId() { return id; };

    public boolean getVendor() {
        if (vendor != null) {
            return vendor;
        } else {
            return false; // Otra acción por defecto en caso de que vendor sea nulo
        }
    }

    public void setVendor(boolean vendor) {
        this.vendor = vendor;
    }
}
