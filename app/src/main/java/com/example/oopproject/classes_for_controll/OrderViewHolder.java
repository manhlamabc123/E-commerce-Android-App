package com.example.oopproject.classes_for_controll;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.interfaces.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderDate, orderAddress, orderPrice;
    public ItemClickListener listener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        orderDate = (TextView) itemView.findViewById(R.id.order_date);
        orderAddress = (TextView) itemView.findViewById(R.id.order_address);
        orderPrice = (TextView) itemView.findViewById(R.id.order_price);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
