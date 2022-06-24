package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oopproject.classes.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {

    private String productID = "";
    private String colorID = "";
    private int counter = 0;

    private ImageView minusButton;
    private ImageView plusButton;
    private TextView textCounter;

    private TextView productName;
    private TextView productWarranty;
    private TextView productPrice;
    private TextView productCategory;
    private TextView productDescription;
    private TextView productInclude;
    private TextView productManufacturer;
    private TextView productMemory;
    private TextView productOS;
    private TextView productRAM;
    private TextView productScreen;

    private Button addToCartButton;

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

        productName = (TextView) findViewById(R.id.product_name_details);
        productWarranty = (TextView) findViewById(R.id.product_warranty_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productCategory = (TextView) findViewById(R.id.product_category_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productInclude = (TextView) findViewById(R.id.product_include_details);
        productManufacturer = (TextView) findViewById(R.id.product_manufacturer_details);
        productMemory = (TextView) findViewById(R.id.product_memory_details);
        productOS = (TextView) findViewById(R.id.product_os_details);
        productRAM = (TextView) findViewById(R.id.product_ram_details);
        productScreen = (TextView) findViewById(R.id.product_screen_details);

        addToCartButton = (Button) findViewById(R.id.product_add_to_cart_btn);
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
//                        ------------------------------Counter Button------------------------------
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//                        ------------------------------------------------------------------------------------------
    }
}