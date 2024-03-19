package com.example.localmarket.model;

import com.example.localmarket.MainActivity;

public class SessionManager {
    public SessionManager(MainActivity mainActivity) {
    }


    public boolean isLoggedIn() {
        return false;
    }
    public boolean isVendor(){
        return true;
    }

    public String getAuthToken() {
        return "";
    }
}
