package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.oopproject.classes.Details;
import com.example.oopproject.classes.Order;
import com.example.oopproject.classes.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private Spinner productColor;

    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
//                        ------------------------------Data from Home Activity------------------------------
        productID = getIntent().getStringExtra("productID");
//                        ------------------------------------------------------------------------------------------
//                        ------------------------------Connect to UI------------------------------
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
        productColor = (Spinner) findViewById(R.id.product_color_picker);

        addToCartButton = (Button) findViewById(R.id.product_add_to_cart_btn);
//                        ------------------------------------------------------------------------------------------
//                        ------------------------------Counter Button------------------------------
        textCounter.setText(String.valueOf(counter));
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter > 0) {
                    counter--;
                    textCounter.setText(String.valueOf(counter));
                }
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
//                        ------------------------------Add to Cart Button------------------------------
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });
//                        ------------------------------------------------------------------------------------------
//                        ------------------------------Display Product's Detail------------------------------
        DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Product").child(productID);
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    productName.setText("Name: " + product.getName());
                    productWarranty.setText("Warranty: " + product.getWarranty());
                    productCategory.setText("Category: " + product.getCategory());
                    productDescription.setText("Description: " + product.getDescription());
                    productInclude.setText("Include: " + product.getInclude());
                    productManufacturer.setText("Manufacturer: " + product.getManufacturer());
                    productOS.setText("OS: " + product.getOs());
                    productScreen.setText("Screen: " + product.getScreen());

    //                         ------------------------------Color Picker------------------------------
                    final List<String> colorsPicker = new ArrayList<String>();
                    for (int i = 0; i < product.getDetails().size(); i++) {
                        colorsPicker.add(product.getDetails().get(i).getColor());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ProductDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, colorsPicker); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    productColor.setAdapter(spinnerArrayAdapter);
                    productColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            productPrice.setText("Price: " + product.getDetails().get(i).getPriceBuy());
                            productMemory.setText("Memory: " + product.getDetails().get(i).getMemory());
                            productRAM.setText("RAM: " + product.getDetails().get(i).getRam());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
    //                        ------------------------------------------------------------------------------------------
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//                        ------------------------------------------------------------------------------------------
    }

    private void addingToCartList(){
    //  -------------------------Get the today's date------------------------------
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-mm-dd");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
    //  ---------------------------------------------------------------------------
    }
}