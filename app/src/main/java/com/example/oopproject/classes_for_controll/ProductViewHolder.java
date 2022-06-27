package com.example.oopproject.classes_for_controll;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.interfaces.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textProductName, textProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(View itemView) {
        super(itemView);

//        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        textProductName = (TextView) itemView.findViewById(R.id.product_name);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}