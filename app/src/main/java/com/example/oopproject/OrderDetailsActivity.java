package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oopproject.Prevalent.Prevalent;
import com.example.oopproject.classes.Order;
import com.example.oopproject.classes.Product;
import com.example.oopproject.classes_for_controll.OrderProductViewHolder;
import com.example.oopproject.classes_for_controll.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderDetailsActivity extends AppCompatActivity {

    private String orderId = "";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference orderDetailReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderId = getIntent().getStringExtra("orderID");
        //-------------------------Get product's data from server-------------------------
        orderDetailReference = FirebaseDatabase.getInstance().getReference().child("Order").child(orderId).child("products");
        //-----------------------------------------------------------------

        //-------------------------Show Order on screen-------------------------
        recyclerView = findViewById(R.id.order_product_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //-----------------------------------------------------------------
    }

    @Override protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(orderDetailReference, Product.class)
                        .build();

        FirebaseRecyclerAdapter<Product, OrderProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, OrderProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position, @NonNull Product model) {

//                        ------------------------------Retrieve Info from Database------------------------------
                        holder.orderProductName.setText(model.getName());
                        holder.orderProductColor.setText(model.getDetails().get(0).getColor());
                        holder.orderProductPrice.setText(model.getPriceInMoneyFormat(0));
                        holder.orderProductQuantity.setText(String.valueOf((int)model.getDetails().get(0).getQuantity()));
//                        ------------------------------------------------------------------------------------------

//                        ------------------------------to Product Details Activity------------------------------
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(OrderDetailsActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("productID", model.getId());
                                startActivity(intent);
                            }
                        });
//                        ------------------------------------------------------------------------------------------
                    }

                    @NonNull
                    @Override
                    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                        OrderProductViewHolder holder = new OrderProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}