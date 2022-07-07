package com.example.oopproject.classes_for_controll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.classes.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Product> cart;

    public CartAdapter(ArrayList<Product> cart) {
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        DecimalFormat REAL_FORMATTER = new DecimalFormat("0.###");
        holder.productQuantity.setText(REAL_FORMATTER.format(cart.get(position).getDetails().get(0).getQuantity()));
        holder.productName.setText(cart.get(position).getName());
        holder.productPrice.setText(REAL_FORMATTER.format(cart.get(position).getDetails().get(0).getPrice()));
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.cart_product_name);
            productPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
            productQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
        }
    }
}
