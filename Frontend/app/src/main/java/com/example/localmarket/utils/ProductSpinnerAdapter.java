package com.example.localmarket.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.localmarket.model.Product;
import com.example.localmarket.R;

import java.util.List;

public class ProductSpinnerAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;

    public ProductSpinnerAdapter(@NonNull Context context, @NonNull List<Product> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);
        }

        TextView textProductName = convertView.findViewById(R.id.textViewProductName);
        ImageView imageViewProduct = convertView.findViewById(R.id.imageProduct);

        Product product = getItem(position);
        if (product != null) {
            textProductName.setText(product.getName());
            imageViewProduct.setImageResource(product.getCategoriaId());
        }

        return convertView;
    }

    // Asegúrate de sobrescribir getDropDownView si quieres personalizar la vista del dropdown también
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
