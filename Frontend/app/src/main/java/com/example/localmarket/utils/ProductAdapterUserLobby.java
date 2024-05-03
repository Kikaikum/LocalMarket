package com.example.localmarket.utils;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ProductAdapterUserLobby extends RecyclerView.Adapter<ProductAdapterUserLobby.ProductViewHolder> {

    private final List<Product> productList;
    private final OnProductClickListener listener;
    private static Map<Integer, Integer> agricultorColorMap = null;
    private static AuthService authService;

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
        private final TextView textNombreAgricultor;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textViewProductName);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
            viewIndicator = itemView.findViewById(R.id.viewIndicator);
            textNombreAgricultor = itemView.findViewById(R.id.tvNombreAgricultor);
        }

        public void bind(Product product, final OnProductClickListener listener) {
            textProductName.setText(product.getName());
            imageViewProduct.setImageResource(product.getCategoriaId());
            int idAgricultor = product.getIdAgricultor();
            int indicatorColor = getColorForFarmerId(idAgricultor);
            viewIndicator.setBackgroundColor(indicatorColor);

            authService = AuthService.getInstance();

            // Retrieve the agricultor name using the agricultor ID
            authService.getUserProfile(idAgricultor, new AuthService.ProfileCallback() {
                @Override
                public void onSuccess(User userProfile) {
                    if (userProfile != null) {
                        // Set the agricultor name to the TextView
                        textNombreAgricultor.setText(userProfile.getUsername());
                    }
                }


                @Override
                public void onError(Throwable t) {
                    Log.e("ProductAdapter", "Error retrieving agricultor profile: " + t.getMessage());
                }
            });
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
        int red = (idAgricultor * 73) % 256; // Factor de rojo más grande
        int green = (idAgricultor * 113) % 256; // Factor de verde más grande
        int blue = (idAgricultor * 157) % 256; // Factor de azul más grande
        // Agregar un valor aleatorio pequeño a cada componente de color
        red = (red + (int) (Math.random() * 32)) % 256;
        green = (green + (int) (Math.random() * 32)) % 256;
        blue = (blue + (int) (Math.random() * 32)) % 256;
        return Color.rgb(red, green, blue);
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}



