package com.example.localmarket.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String userName;
    private Boolean isVendor;

    public SignUpRequest(String email, String password, String userName, String name, String surname, Boolean isVendor) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isVendor=isVendor;
        this.userName= userName;
        this.surname = surname;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Boolean getVendor() { return isVendor; }

    public void setVendor(Boolean vendor) { isVendor = vendor; }

}