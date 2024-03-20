package com.example.localmarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;
    private static TokenManager instance;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}
