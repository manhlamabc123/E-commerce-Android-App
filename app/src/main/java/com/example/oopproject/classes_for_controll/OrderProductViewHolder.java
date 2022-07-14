package com.example.oopproject.classes_for_controll;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.interfaces.ItemClickListener;

public class OrderProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderProductName, orderProductColor, orderProductPrice, orderProductQuantity;
    public ItemClickListener listener;

    public OrderProductViewHolder(View itemView) {
        super(itemView);

        orderProductColor = (TextView) itemView.findViewById(R.id.cart_product_color);
        orderProductName = (TextView) itemView.findViewById(R.id.cart_product_name);
        orderProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
        orderProductQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
