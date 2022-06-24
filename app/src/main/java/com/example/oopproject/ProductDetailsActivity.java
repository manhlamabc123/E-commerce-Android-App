package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailsActivity extends AppCompatActivity {

    private String productID = "";
    private String colorID = "";
    private int counter = 0;

    private ImageView minusButton;
    private ImageView plusButton;
    private TextView textCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
//                        ------------------------------Connect to UI------------------------------
        productID = getIntent().getStringExtra("productID");
        colorID = getIntent().getStringExtra("colorID");

        minusButton = (ImageView) findViewById(R.id.image_minus);
        plusButton = (ImageView) findViewById(R.id.image_plus);
        textCounter = (TextView) findViewById(R.id.text_counter);
//                        ------------------------------------------------------------------------------------------
//                        ------------------------------Counter Button------------------------------
        textCounter.setText(String.valueOf(counter));
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                textCounter.setText(String.valueOf(counter));
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                textCounter.setText(String.valueOf(counter));
            }
        });
//                        ------------------------------------------------------------------------------------------
    }
}