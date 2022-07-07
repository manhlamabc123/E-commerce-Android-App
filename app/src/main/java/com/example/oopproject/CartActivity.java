package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oopproject.Prevalent.Prevalent;
import com.example.oopproject.classes.Product;
import com.example.oopproject.classes_for_controll.CartAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private Button nextProcessBtn;
    private TextView textTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //--------------------------Connect to UI--------------------------
        recyclerView = findViewById(R.id.cart_list);
        nextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        textTotalAmount = (TextView) findViewById(R.id.total_price);
        //------------------------------------------------------------------------------

        //--------------------------Recycler View--------------------------
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CartAdapter(Prevalent.currentCustomer.getCart());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //------------------------------------------------------------------------------

        //--------------------------Total Price Text--------------------------
        textTotalAmount.setText(Prevalent.currentCustomer.getTotalPriceOfCart());
        //------------------------------------------------------------------------------

        //------------------------------Next Button--------------------------
        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------
    }
}