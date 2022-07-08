package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        paymentMethodSpinner = (Spinner) findViewById(R.id.payment_method_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConfirmFinalOrderActivity.this, R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);
    }
}