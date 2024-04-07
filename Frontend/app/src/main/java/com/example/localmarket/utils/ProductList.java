package com.example.localmarket.utils;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    // Método para obtener una lista de productos de muestra
    public static List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1,R.drawable.apple_whole_18, "Apple", "Delicious apple", "Per unit", 1.5));
        productList.add(new Product(2,R.drawable.banana_18, "Banana", "Fresh banana", "Per kg", 2.0));
        productList.add(new Product(3,R.drawable.apple_whole_18, "Apple", "Delicious apple", "Per unit", 1.5));
        productList.add(new Product(4,R.drawable.banana_18, "Banana", "Fresh banana", "Per kg", 2.0));
        productList.add(new Product(5,R.drawable.apple_whole_18, "Apple", "Delicious apple", "Per unit", 1.5));
        productList.add(new Product(6,R.drawable.banana_18, "Banana", "Fresh banana", "Per kg", 2.0));
        productList.add(new Product(7,R.drawable.apple_whole_18, "Apple", "Delicious apple", "Per unit", 1.5));
        productList.add(new Product(8,R.drawable.banana_18, "Banana", "Fresh banana", "Per kg", 2.0));

        // Agrega más productos aquí según sea necesario
        return productList;
    }
}
