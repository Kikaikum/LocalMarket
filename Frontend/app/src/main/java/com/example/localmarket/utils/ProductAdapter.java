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

/**
 * Adaptador para mostrar una lista de productos en un RecyclerView.
 * @author Oriol + Ainoha
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductClickListener listener;

    /**
     * Constructor que acepta la lista de productos y el listener para manejar los clics en los productos.
     * @author Ainoha
     * @param productList La lista de productos a mostrar.
     * @param listener    El listener para manejar los clics en los productos.
     */
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
     * @author oriol
     * @param holder   El ViewHolder al que se van a vincular los datos del producto.
     * @param position La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    /**
     * Obtiene el número total de elementos en el conjunto de datos del adaptador.
     * @author Oriol
     * @return El número total de elementos en el conjunto de datos del adaptador.
     */
    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * ViewHolder para mostrar un producto en un RecyclerView.
     * @author Oriol
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textProductName;
        private ImageView imageViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textViewProductName);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
        }

        /**
         * Vincula los datos del producto al ViewHolder y agrega un listener de clic al itemView.
         *
         * @param product  El producto a mostrar.
         * @param listener El listener para manejar los clics en los productos.
         * @author Ainoha
         */
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

    /**
     * Interfaz para manejar los clics en los productos.
     * @author Ainoha
     */
    public interface OnProductClickListener {
        /**
         * Método llamado cuando se hace clic en un producto.
         *
         * @param product El producto en el que se hizo clic.
         */
        void onProductClick(Product product);
    }
}
