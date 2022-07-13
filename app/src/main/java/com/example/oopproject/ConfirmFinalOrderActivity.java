package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oopproject.classes_for_controll.Prevalent;
import com.example.oopproject.classes.Address;
import com.example.oopproject.classes.Customer;
import com.example.oopproject.classes.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner;
    private EditText paymentName;
    private EditText paymentPhoneNumber;
    private Spinner paymentProvide;
    private Spinner paymentDistrict;
    private Spinner paymentWard;
    private EditText paymentAddressDetail;
    private Spinner paymentMethod;
    private TextView paymentTotalPrice;
    private Button confirmButton;

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
        confirmButton = (Button) findViewById(R.id.confirm_payment_btn);
        paymentAddressDetail = (EditText) findViewById(R.id.payment_address_details);
        //---------------------------------------------------------------

        //---------------------Payment Method Spinner---------------------
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConfirmFinalOrderActivity.this, R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);
        //---------------------------------------------------------------

        //---------------------Info to UI---------------------
        paymentTotalPrice.setText(Prevalent.getCurrentCustomer().getTotalPriceOfCart());
        paymentName.setText(Prevalent.getCurrentCustomer().getName());
        paymentPhoneNumber.setText(Prevalent.getCurrentCustomer().getPhone());
        //---------------------------------------------------------------

        //---------------------Confirm Button---------------------
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---------------------------Create Order---------------------------
                getChildCount(new FirebaseCallback() {
                    @Override
                    public void onCallback(int childCount) {
                        String orderID = "";
                        System.out.println(childCount);
                        if (childCount <= 9) orderID = "GD00" + childCount;
                        else if (childCount <= 99) orderID = "GD0" + childCount;
                        else if (childCount <= 999) orderID = "GD" + childCount;
                        else {
                            Toast.makeText(ConfirmFinalOrderActivity.this, "You have order too much", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Date today = new Date();

                        if (paymentAddressDetail.getText().toString().matches("")) {
                            Toast.makeText(ConfirmFinalOrderActivity.this, "Please enter your address.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Address address = new Address(paymentAddressDetail.getText().toString().trim(), "A", "B", "C");

                        Order order = new Order(orderID,
                                Prevalent.getCurrentCustomer().getPhone(),
                                today,
                                address,
                                Prevalent.getCurrentCustomer().getCart());
                        System.out.println(order.getId());
                        FirebaseDatabase.getInstance().getReference().child("Order").child(orderID).
                                updateChildren(order.toMap());

                        //---------------------------On Server: Delete Cart---------------------------
                        FirebaseDatabase.getInstance().getReference().child("Customer").
                                child(Prevalent.getCurrentCustomer().getPhone()).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Customer customer = snapshot.getValue(Customer.class);
                                            customer.removeAllProducts();
                                            Prevalent.setCurrentCustomer(customer);

                                            FirebaseDatabase.getInstance().getReference().child("Customer").
                                                    child(customer.getPhone()).
                                                    updateChildren(customer.toMap()).
                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Your Order had been confirmed", Toast.LENGTH_LONG).show();
                                                            //---------------------------To Home---------------------------
                                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                                            startActivity(intent);
                                                            //---------------------------------------------------------------
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                        //---------------------------------------------------------------
                    }
                });
                //---------------------------------------------------------------------------------
            }
        });
        //---------------------------------------------------------------
    }

    private void getChildCount (FirebaseCallback firebaseCallback) {
        FirebaseDatabase.getInstance().getReference().child("Order").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int childCount = 0;
                        if (snapshot.exists()) {
                            childCount = (int) snapshot.getChildrenCount();
                        }
                        firebaseCallback.onCallback(childCount + 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //-----------------------Interface for "Using many Firebase event listener at the same time"------------------------------
    private interface FirebaseCallback {
        void onCallback(int childCount);
    }
    //------------------------------------------------------------------------------------------------------------------------
}