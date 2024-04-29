package com.example.localmarket.context;

import android.content.Context;

import com.example.localmarket.utils.TokenManager;

public class AndroidContextProvider implements ContextProvider {
    private final Context context;

    public AndroidContextProvider(Context context) {
        this.context = context;
    }



    @Override
    public TokenManager getTokenManager() {
        return TokenManager.getInstance(context);
    }

    @Override
    public Context getContext() {
        return context;
    }
}