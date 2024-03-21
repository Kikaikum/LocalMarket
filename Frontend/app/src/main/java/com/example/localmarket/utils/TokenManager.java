package com.example.localmarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USERNAME="username";


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
    public void saveUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, 123);
    }
    public String getUserUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "aaa123");
    }
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}

