package com.example.localmarket.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.model.Order;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;

import java.util.List;
import java.util.Map;

public class OrderAdapterSeller extends RecyclerView.Adapter<OrderAdapterSeller.OrderViewHolder> {
    private List<Map<String, Integer>> orderList;
    private OnOrderClickListener listener;
    private TextView userNameOrder;
    private int clientId;

    AuthService authService;
    public OrderAdapterSeller(List<Map<String, Integer>> orderList, OnOrderClickListener listerner) {
        this.orderList = orderList;
        this.listener = listerner;
        authService= new AuthService();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_seller, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Map<String, Integer> order = orderList.get(position);
        holder.bind(order,listener);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameOrder;
        private TextView userNamOrder;
        private  int clientId;
        private  AuthService authService= AuthService.getInstance();
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameOrder= itemView.findViewById(R.id.tvNameOfUserOrder);

        }

        public void bind(Map<String, Integer> order, OnOrderClickListener listener) {
            clientId = (order.get("clientId"));
            authService.getUserProfile(clientId, new AuthService.ProfileCallback() {
                @Override
                public void onSuccess(User userProfile) {
                    userNameOrder.setText(userProfile.getName()+" "+userProfile.getSurname());
                }

                @Override
                public void onError(Throwable t) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onOrderClick();
                }
            });
        }
    }
    public interface OnOrderClickListener {
        /**
         * Método llamado cuando se hace clic en un producto.
         *
         * @param order El pedido en el que se hizo clic.
         */
        void onOrderClick(Order order);
    }
}
