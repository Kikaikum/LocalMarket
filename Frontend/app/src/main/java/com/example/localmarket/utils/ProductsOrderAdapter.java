package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;

import java.util.List;

public class ProductsOrderAdapter extends RecyclerView.Adapter<ProductsOrderAdapter.ProductViewHolder> {
    private List<Product> products;

    public ProductsOrderAdapter(List<Product> products) {
        this.products = products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);
        return new ProductViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        // Asigna los datos del producto a los elementos de la vista
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Elementos de la vista del producto
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa los elementos de la vista
        }
    }
}
