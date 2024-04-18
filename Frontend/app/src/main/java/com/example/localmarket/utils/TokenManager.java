package com.example.localmarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase para manejar el token de autenticación y otros datos almacenados en SharedPreferences.
 *
 * Esta clase proporciona métodos para guardar, recuperar y eliminar el token de autenticación, así como otros datos relevantes.
 *
 * @author Oriol Estero Sanchez
 */
public class TokenManager {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PRODUCT_ID="productId";


    private SharedPreferences sharedPreferences;
    private static TokenManager instance;
    private Context applicationContext;

    public  TokenManager() {

    }

    public TokenManager(Context context) {
        applicationContext = context.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Método estático para obtener una instancia única de TokenManager.
     *
     * @param context El contexto de la aplicación.
     * @return Una instancia única de TokenManager.
     */
    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }
    public Context getContext() {
        return applicationContext;
    }
    /**
     * Método para guardar el token de autenticación en SharedPreferences.
     *
     * @param token El token de autenticación a guardar.
     */
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    /**
     * Método para guardar el ID de usuario en SharedPreferences.
     *
     * @param userId El ID de usuario a guardar.
     */
    public void saveUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    /**
     * Método para guardar el nombre de usuario en SharedPreferences.
     *
     * @param username El nombre de usuario a guardar.
     */
    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    /**
     * Método para guardar el ID del producto en SharedPreferences.
     *
     * @param productId El ID del producto a guardar.
     */
    public void saveProductId(int productId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_PRODUCT_ID, productId);
        editor.apply();
    }

    /**
     * Método para obtener el ID de usuario guardado en SharedPreferences.
     *
     * @return El ID de usuario guardado.
     */
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, 123);
    }

    /**
     * Método para obtener el nombre de usuario guardado en SharedPreferences.
     *
     * @return El nombre de usuario guardado.
     */
    public String getUserUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "aaa123");
    }

    /**
     * Método para obtener el token de autenticación guardado en SharedPreferences.
     *
     * @return El token de autenticación guardado.
     */
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    /**
     * Método para obtener el ID del producto guardado en SharedPreferences.
     *
     * @return El ID del producto guardado.
     */
    public int getProductId(){return sharedPreferences.getInt(KEY_PRODUCT_ID,123);}

    /**
     * Método para eliminar el token de autenticación y el ID de usuario de SharedPreferences (cerrar sesión).
     */
    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USER_ID);
        editor.apply();
    }

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

