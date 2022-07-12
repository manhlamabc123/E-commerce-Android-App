package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.oopproject.Prevalent.Prevalent;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner;
    private EditText paymentName;
    private EditText paymentPhoneNumber;
    private Spinner paymentProvide;
    private Spinner paymentDistrict;
    private Spinner paymentWard;
    private Spinner paymentMethod;
    private TextView paymentTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        //---------------------Connect to UI---------------------
        paymentName = (EditText) findViewById(R.id.payment_name);
        paymentPhoneNumber = (EditText) findViewById(R.id.payment_phone_number);
        paymentProvide = (Spinner) findViewById(R.id.payment_province_spinner);
        paymentDistrict = (Spinner) findViewById(R.id.payment_district_spinner);
        paymentWard = (Spinner) findViewById(R.id.payment_ward_spinner);
        paymentMethodSpinner = (Spinner) findViewById(R.id.payment_method_spinner);
        paymentTotalPrice = (TextView) findViewById(R.id.payment_total_price_text);
        //---------------------------------------------------------------

        //---------------------Payment Method Spinner---------------------
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConfirmFinalOrderActivity.this, R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);
        //---------------------------------------------------------------

        //---------------------Payment Total Price---------------------
        paymentTotalPrice.setText(Prevalent.currentCustomer.getTotalPriceOfCart());
        //---------------------------------------------------------------
    }
}