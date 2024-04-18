package com.example.localmarket.context;

import android.content.Context;

import com.example.localmarket.utils.TokenManager;

public interface ContextProvider {
    TokenManager getTokenManager();
    Context getContext();
}