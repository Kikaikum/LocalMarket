package com.example.localmarket.utils;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    // Método para obtener una lista de productos de muestra
    public static List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Apple",R.drawable.apple_whole_18, 1, "Delicious apple", "Per unit", 1.5));
        productList.add(new Product("Banana",R.drawable.banana_18, 2, "Fresh banana", "Per kg", 2.0))
        ;productList.add(new Product("Apple",R.drawable.apple_whole_18, 1, "Delicious apple", "Per unit", 1.5));
        productList.add(new Product("Banana",R.drawable.banana_18, 2, "Fresh banana", "Per kg", 2.0));
        productList.add(new Product("Apple",R.drawable.apple_whole_18, 1, "Delicious apple", "Per unit", 1.5));
        productList.add(new Product("Banana",R.drawable.banana_18, 2, "Fresh banana", "Per kg", 2.0));
        productList.add(new Product("Apple",R.drawable.apple_whole_18, 1, "Delicious apple", "Per unit", 1.5));
        productList.add(new Product("Banana",R.drawable.banana_18, 2, "Fresh banana", "Per kg", 2.0));
        productList.add(new Product("Apple",R.drawable.apple_whole_18, 1, "Delicious apple", "Per unit", 1.5));
        productList.add(new Product("Banana",R.drawable.banana_18, 2, "Fresh banana", "Per kg", 2.0));


        // Agrega más productos aquí según sea necesario
        return productList;
    }
}
