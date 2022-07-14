package com.example.oopproject.classes_for_controll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.classes.Employee;
import com.example.oopproject.classes.Product;
import com.example.oopproject.interfaces.ItemClickListener;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final ItemClickListener itemClickListener;
    private ArrayList<Product> productArrayList;

    public ProductAdapter(ItemClickListener itemClickListener, ArrayList<Product> productArrayList) {
        this.itemClickListener = itemClickListener;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.product_name);

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
