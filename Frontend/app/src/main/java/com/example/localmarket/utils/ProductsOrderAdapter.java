package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Product;
import com.example.localmarket.network.service.AuthService;

import java.util.List;
import java.util.Map;

public class ProductsOrderAdapter extends RecyclerView.Adapter<ProductsOrderAdapter.ProductViewHolder> {
    private List<Map<String, Integer>> pedidoItems;
    private AuthService authService;



    public ProductsOrderAdapter(List<Map<String, Integer>> pedidoItems) {
        this.pedidoItems = pedidoItems;
        this.authService = new AuthService();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Map<String, Integer> item = pedidoItems.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return pedidoItems.size();
    }

    public  class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvQuantity;
        private ImageView imageView;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.cantidad);
            imageView = itemView.findViewById(R.id.imageProductItem);
        }

        public void bind(Map<String, Integer> item) {


            Integer productId = item.get("itemId");
            Integer quantity = item.get("cantidad");
            authService.getProductById(productId, new AuthService.AuthCallback<Product>() {
                @Override
                public void onSuccess(Product response) {
                    tvProductName.setText(response.getName());
                    imageView.setImageResource(response.getCategoriaId());
                    if (response.getUnidadMedida()=="Peso"){
                        tvQuantity.setText("Cantidad: "+quantity+" Kg");
                    }else {
                        tvQuantity.setText("Cantidad: "+quantity+" Unidad/es");
                    }
                }

                @Override
                public void onError(Throwable t) {

                }
            });



                tvQuantity.setText(String.format("Cantidad: %s", quantity));
                imageView.setImageResource(R.drawable.apple_whole_18);

        }
    }
}
