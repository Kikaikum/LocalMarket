package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.model.Product;
import com.example.localmarket.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductClickListener listener;

    // Constructor que acepta la lista de productos y el listener
    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener; // Asignar el listener
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);
        return new ProductViewHolder(view);
    }


    /**
     * Vincula los datos del producto al ViewHolder dado y agrega un listener de clic al itemView.
     *
     * @param holder   El ViewHolder al que se van a vincular los datos del producto.
     * @param position La posición del elemento dentro del conjunto de datos del adaptador.
     */
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

        private TextView textProductName;
        private ImageView imageViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textViewProductName);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
        }

        public void bind(Product product, final OnProductClickListener listener) {
            textProductName.setText(product.getName());
            // Asignar la imagen al ImageView
            imageViewProduct.setImageResource(product.getCategoriaId());


            // Manejar clics en el elemento
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductClick(product);
                }
            });
        }
    }
    // Interfaz para manejar clics en los productos
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}
