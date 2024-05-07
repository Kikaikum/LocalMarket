package com.example.localmarket.utils;

import android.util.Log;
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
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar los elementos del carrito de compras.
 * @author Ainoha
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<CartItem> cartItemList;
    private OnCartItemChangedListener onCartItemChangedListener;

    /**
     * Interfaz para manejar los cambios en los elementos del carrito.
     */
    public interface OnCartItemChangedListener {
        void onCartItemChanged();
    }

    /**
     * Establece el listener para los cambios en los elementos del carrito.
     *
     * @param listener El listener de cambios en los elementos del carrito.
     */
    public void setOnCartItemChangedListener(OnCartItemChangedListener listener) {
        this.onCartItemChangedListener = listener;
    }

    /**
     * Establece la lista de elementos del carrito.
     *
     * @param cartItemList La lista de elementos del carrito.
     */
    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    /**
     * Obtiene una copia de la lista de elementos del carrito.
     *
     * @return Una copia de la lista de elementos del carrito.
     */
    public ArrayList<CartItem> getCartItemList() {
        return new ArrayList<>(cartItemList);
    }

    /**
     * Constructor de la clase OrderAdapter.
     *
     * @param cartItemList La lista de elementos del carrito.
     */
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

    /**
     * Clase interna para representar las vistas de los elementos del carrito.
     */
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView quantityTextView;
        private ImageView imageViewProduct;
        private TextView precioTextView;
        private TextView unidadMedidaTextView;
        private TextView buttonIncrement;
        private TextView buttonDecrement;
        private ImageView buttonDelete;
        private TextView agricutlorUsername;


        /**
         * Constructor de la clase OrderViewHolder.
         *
         * @param itemView La vista del elemento del carrito.
         */
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
            agricutlorUsername=itemView.findViewById(R.id.textViewAgricultorUsername);

        }

        /**
         * Vincula los datos del elemento del carrito con las vistas correspondientes.
         *
         * @param cartItem El objeto CartItem que representa el elemento del carrito.
         */
        public void bind(CartItem cartItem) {

            productNameTextView.setText(cartItem.getProductName());
            quantityTextView.setText(String.valueOf(cartItem.getCantidad()));
            precioTextView.setText(String.format("%.2f", cartItem.getPrice()));
            unidadMedidaTextView.setText(cartItem.getUnidadMedida());
            imageViewProduct.setImageResource(cartItem.getCategoriaId());
             int agricultorId= cartItem.getAgricultorId();
            // Accede al servicio de autenticación para obtener el perfil del usuario y recuperar el nombre del agricultor
            AuthService authService = new AuthService();
            authService.getUserProfile(agricultorId, new AuthService.ProfileCallback() {
                @Override
                public void onSuccess(User userProfile) {
                    if (userProfile != null) {
                        // Obtiene el nombre del agricultor del perfil del usuario
                        String agricultorName = userProfile.getUsername();

                        // Establece el nombre del agricultor en el TextView correspondiente
                        agricutlorUsername.setText(agricultorName);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    // Maneja el error al obtener el perfil del usuario
                    Log.e("OrderAdapter", "Error al obtener el perfil del usuario: " + t.getMessage());
                }
            });

            buttonIncrement.setOnClickListener(v -> {
                // Lógica para incrementar la cantidad
                int currentQuantity = Integer.parseInt(quantityTextView.getText().toString());
                currentQuantity++;
                quantityTextView.setText(String.valueOf(currentQuantity));
                // Actualiza la cantidad en el objeto CartItem correspondiente
                cartItem.setCantidad(currentQuantity);
                notifyCartItemChanged();
            });

            buttonDecrement.setOnClickListener(v -> {
                // Lógica para decrementar la cantidad
                int currentQuantity = Integer.parseInt(quantityTextView.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantityTextView.setText(String.valueOf(currentQuantity));
                    // Actualiza la cantidad en el objeto CartItem correspondiente
                    cartItem.setCantidad(currentQuantity);
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

        /**
         * Notifica al listener de cambios en los elementos del carrito.
         */
        private void notifyCartItemChanged() {
            if (onCartItemChangedListener != null) {
                onCartItemChangedListener.onCartItemChanged();
            }
        }
    }
}
