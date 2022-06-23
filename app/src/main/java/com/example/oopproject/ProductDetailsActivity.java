package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProductDetailsActivity extends AppCompatActivity {

    private String productID = "";
    private String colorID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("productID");
        colorID = getIntent().getStringExtra("colorID");
    }
}