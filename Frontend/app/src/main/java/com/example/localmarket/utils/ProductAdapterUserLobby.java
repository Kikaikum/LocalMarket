package com.example.localmarket.utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ProductAdapterUserLobby extends RecyclerView.Adapter<ProductAdapterUserLobby.ProductViewHolder> {
    private static final int RED_FACTOR = 23;
    private static final int GREEN_FACTOR = 37;
    private static final int BLUE_FACTOR = 53;

    private final List<Product> productList;
    private final OnProductClickListener listener;
    private static Map<Integer, Integer> agricultorColorMap = null;

    public ProductAdapterUserLobby(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
        this.agricultorColorMap = new HashMap<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product_userlobby, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView textProductName;
        private final ImageView imageViewProduct;
        private final View viewIndicator;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textViewProductName);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
            viewIndicator = itemView.findViewById(R.id.viewIndicator);
        }

        public void bind(Product product, final OnProductClickListener listener) {
            textProductName.setText(product.getName());
            imageViewProduct.setImageResource(product.getCategoriaId());
            int idAgricultor = product.getIdAgricultor();
            int indicatorColor = getColorForFarmerId(idAgricultor);
            viewIndicator.setBackgroundColor(indicatorColor);

            itemView.setOnClickListener(v -> listener.onProductClick(product));
        }
    }

    private static int getColorForFarmerId(int idAgricultor) {
        if (agricultorColorMap.containsKey(idAgricultor)) {
            return agricultorColorMap.get(idAgricultor);
        } else {
            int color = generateUniqueColor(idAgricultor);
            agricultorColorMap.put(idAgricultor, color);
            return color;
        }
    }

    private static int generateUniqueColor(int idAgricultor) {
        int red = (idAgricultor * RED_FACTOR) % 256;
        int green = (idAgricultor * GREEN_FACTOR) % 256;
        int blue = (idAgricultor * BLUE_FACTOR) % 256;
        return Color.rgb(red, green, blue);
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}



