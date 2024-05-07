package com.example.localmarket.utils;

import static java.lang.Character.getType;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.localmarket.model.CartItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Clase para gestionar los elementos del carrito de compras.
 * @author Ainoha
 */
public class OrderManager {
    private static final String PREF_NAME = "OrderPrefs";
    private static final String KEY_CART_ITEMS = "cartItems";


    private SharedPreferences sharedPreferences;
    private static OrderManager instance;
    private Context applicationContext;


    /**
     * Constructor de la clase OrderManager.
     *
     * @param context El contexto de la aplicación.
     */
    public OrderManager(Context context) {
        applicationContext = context.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    /**
     * Método estático para obtener la instancia única de OrderManager.
     *
     * @param context El contexto de la aplicación.
     * @return La instancia única de OrderManager.
     */
    public static synchronized OrderManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrderManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Método para obtener el contexto de la aplicación.
     *
     * @return El contexto de la aplicación.
     */
    public Context getContext() {
        return applicationContext;
    }

    // Métodos para guardar y recuperar datos del carrito

    /**
     * Guarda los elementos del carrito de compras en las preferencias compartidas.
     *
     * @param cartItems La lista de elementos del carrito de compras.
     */
    public void saveCartItem(ArrayList<CartItem> cartItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(KEY_CART_ITEMS, json);
        editor.apply();
    }

    /**
     * Obtiene los elementos del carrito de compras guardados en las preferencias compartidas.
     *
     * @return La lista de elementos del carrito de compras.
     */
    public ArrayList<CartItem> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, "");
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<CartItem>>(){}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>(); // Devuelve una lista vacía si no hay datos guardados
    }

    /**
     * Elimina todos los elementos del carrito de compras de las preferencias compartidas.
     */
    public void clearCartItems() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }
}