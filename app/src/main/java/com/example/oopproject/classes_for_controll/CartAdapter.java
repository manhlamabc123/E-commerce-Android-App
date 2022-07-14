package com.example.oopproject.classes_for_controll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.classes.Product;
import com.example.oopproject.interfaces.ItemClickListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final ItemClickListener itemClickListener;
    private ArrayList<Product> cart;

    public CartAdapter(ItemClickListener itemClickListener, ArrayList<Product> cart) {
        this.itemClickListener = itemClickListener;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.productQuantity.setText(String.valueOf((int)cart.get(position).getDetails().get(0).getQuantity()));
        holder.productName.setText(cart.get(position).getName());
        holder.productPrice.setText(cart.get(position).getPriceInMoneyFormat(0));
        holder.productColor.setText(cart.get(position).getDetails().get(0).getColor());
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;
        public TextView productColor;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.cart_product_name);
            productPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
            productQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
            productColor = (TextView) itemView.findViewById(R.id.cart_product_color);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            itemClickListener.onClick(view, pos, false);
                        }
                    }
                }
            });
        }
    }
}
