package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Order;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;

import java.util.List;

public class OrderAdapterSeller extends RecyclerView.Adapter<OrderAdapterSeller.OrderViewHolder> {
    private List<Order> orderList;
    private OnOrderClickListener listener;
    private AuthService authService;

    public OrderAdapterSeller(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
        this.authService=new AuthService();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_seller, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order, listener);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClientName;
        private ImageView imageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientName = itemView.findViewById(R.id.tvNameOfUserOrder);
            imageView = itemView.findViewById(R.id.imageChart);
        }

        public void bind(Order order, OnOrderClickListener listener) {
            authService.getUserProfile(order.getIdCliente(), new AuthService.ProfileCallback() {
                @Override
                public void onSuccess(User userProfile) {
                    tvClientName.setText(userProfile.getName() + " " + userProfile.getSurname());
                    imageView.setImageResource(R.drawable.shopping_basket_18);
                }

                @Override
                public void onError(Throwable t) {
                    // Manejar el error al obtener el perfil del usuario
                }
            });

        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }
}
