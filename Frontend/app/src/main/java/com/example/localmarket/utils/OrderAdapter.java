package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.CartItem;
import com.example.localmarket.fragments.OrderDetailsFragment;

import java.util.ArrayList;
import java.util.List;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<CartItem> cartItemList;
    private OnCartItemChangedListener onCartItemChangedListener;

    public interface OnCartItemChangedListener {
        void onCartItemChanged();
    }

    public void setOnCartItemChangedListener(OnCartItemChangedListener listener) {
        this.onCartItemChangedListener = listener;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    public ArrayList<CartItem> getCartItemList() {
        return new ArrayList<>(cartItemList);
    }

    public OrderAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_product_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView quantityTextView;
        private ImageView imageViewProduct;
        private TextView precioTextView;
        private TextView unidadMedidaTextView;
        private TextView buttonIncrement;
        private TextView buttonDecrement;
        private ImageView buttonDelete;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.textViewProductName);
            quantityTextView = itemView.findViewById(R.id.textViewQuantity);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
            precioTextView = itemView.findViewById(R.id.textViewPrice);
            unidadMedidaTextView = itemView.findViewById(R.id.textViewUnitMeasure);
            buttonIncrement = itemView.findViewById(R.id.textViewPlus);
            buttonDecrement = itemView.findViewById(R.id.textViewMinus);
            buttonDelete = itemView.findViewById(R.id.deleteproduct);
        }

        public void bind(CartItem cartItem) {
            productNameTextView.setText(cartItem.getProductName());
            quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
            precioTextView.setText(String.format("%.2f", cartItem.getPrice()));
            unidadMedidaTextView.setText(cartItem.getUnidadMedida());
            imageViewProduct.setImageResource(cartItem.getCategoriaId());

            buttonIncrement.setOnClickListener(v -> {
                // Lógica para incrementar la cantidad
                int currentQuantity = Integer.parseInt(quantityTextView.getText().toString());
                currentQuantity++;
                quantityTextView.setText(String.valueOf(currentQuantity));
                // Actualiza la cantidad en el objeto CartItem correspondiente
                cartItem.setQuantity(currentQuantity);
                notifyCartItemChanged();
            });

            buttonDecrement.setOnClickListener(v -> {
                // Lógica para decrementar la cantidad
                int currentQuantity = Integer.parseInt(quantityTextView.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantityTextView.setText(String.valueOf(currentQuantity));
                    // Actualiza la cantidad en el objeto CartItem correspondiente
                    cartItem.setQuantity(currentQuantity);
                    notifyCartItemChanged();
                }
            });

            buttonDelete.setOnClickListener(v -> {
                // Lógica para eliminar el elemento del carrito
                // Elimina el elemento del objeto cartItemList y notifica al adaptador
                int position = getAdapterPosition();
                cartItemList.remove(position);
                notifyItemRemoved(position);
                notifyCartItemChanged();
            });
        }

        private void notifyCartItemChanged() {
            if (onCartItemChangedListener != null) {
                onCartItemChangedListener.onCartItemChanged();
            }
        }
    }
}
