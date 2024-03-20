package com.example.localmarket.model;

public class User {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private int id;
    private Boolean vendor;

    // Constructor
    public User(String name, String surname, String username, String email, String password, int id, Boolean vendor) {
        this.name=name;
        this.surname=surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.vendor= vendor;
    }

    // Getters y setters

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
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
