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

import com.example.oopproject.classes_for_controll.Prevalent;
import com.example.oopproject.classes.Order;
import com.example.oopproject.classes_for_controll.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference orderReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //-------------------------Get product's data from server-------------------------
        orderReference = FirebaseDatabase.getInstance().getReference().child("Order");
        //-----------------------------------------------------------------

        //-------------------------Show Order on screen-------------------------
        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //-----------------------------------------------------------------
    }

    @Override protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(orderReference.orderByChild("customerId").equalTo(Prevalent.getCurrentCustomer().getPhone()), Order.class)
                        .build();

        FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {

                        //------------------------------Retrieve Info from Database------------------------------
                        holder.orderDate.setText(model.getDate());
                        holder.orderAddress.setText(model.getAddress().getAddress());
                        holder.orderPrice.setText(model.getPrice());
                        //------------------------------------------------------------------------------------------

                        //------------------------------to Product Details Activity------------------------------
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(OrderActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("orderID", model.getId());
                                startActivity(intent);
                            }
                        });
                        //------------------------------------------------------------------------------------------
                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                        OrderViewHolder holder = new OrderViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}