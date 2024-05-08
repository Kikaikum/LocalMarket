package com.example.localmarket.utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.fragments.PedidoProductsFragment;
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
        final Order order = orderList.get(position);
        holder.bind(order,listener);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClientName;
        private ImageView imageView;
        private TextView tvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientName = itemView.findViewById(R.id.tvNameOfUserOrder);
            imageView = itemView.findViewById(R.id.imageChart);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Order order = orderList.get(position);
                        listener.onOrderClick(order);
                    }
                }
            });
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
            if (order.getEstado().equals("creado")){
                tvStatus.setText("Estado: "+order.getEstado());
                tvStatus.setTextColor(Color.BLUE);
            }else{
                tvStatus.setText("Estado: "+order.getEstado());
                tvStatus.setTextColor(Color.GREEN);
            }


        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }
}
