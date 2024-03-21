package com.example.localmarket.model;

public class User {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private int id;
    private boolean agricultor;

    // Constructor
    public User(String name, String surname, String username, String email, String password, int id, boolean agricultor) {
        this.name=name;
        this.surname=surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.agricultor= agricultor;
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

    public boolean getAgricultor() { return agricultor; }

    public void setAgricultor(boolean agricultor) {
        this.agricultor = agricultor;
    }
}
