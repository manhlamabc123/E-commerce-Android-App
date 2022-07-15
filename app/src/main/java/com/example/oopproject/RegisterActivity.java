package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oopproject.classes.Address;
import com.example.oopproject.classes.Customer;
import com.example.oopproject.classes_for_controll.SelectAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword, InputDetailAddress;
    private ProgressDialog loadingBar;
    private String selectedProvince, selectedDistrict, selectedCommune;
    private Spinner provinceSpinner, districtSpinner, communeSpinner;
    private SelectAddress selectAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //-----------------------Connect to UI----------------------------------------------
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputDetailAddress = (EditText) findViewById(R.id.register_detail_address_input);
        loadingBar = new ProgressDialog(this);
        provinceSpinner = (Spinner)findViewById(R.id.spinner_province);
        districtSpinner = (Spinner)findViewById(R.id.spinner_district);
        communeSpinner = (Spinner)findViewById(R.id.spinner_commune);
        //--------------------------------------------------------------------------------------------

        //-----------------------Address's Spinner----------------------------------------------
        selectAddress = new SelectAddress(this, provinceSpinner, districtSpinner, communeSpinner, R.layout.spinner_layout);
        //--------------------------------------------------------------------------------------------

        //-----------------------Create Button----------------------------------------------
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        //--------------------------------------------------------------------------------------------
    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phoneNumber = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String detailAddress = InputDetailAddress.getText().toString();

        selectedProvince = selectAddress.getSelectedProvince();
        selectedDistrict = selectAddress.getSelectedDistrict();
        selectedCommune = selectAddress.getSelectedCommune();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumber))
        {
            Toast.makeText(this, "Please write your phone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedProvince.equals(""))
        {
            Toast.makeText(this, "Please select province...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedDistrict.equals(""))
        {
            Toast.makeText(this, "Please select district...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedCommune.equals(""))
        {
            Toast.makeText(this, "Please select commune...", Toast.LENGTH_SHORT).show();
        }
        else if (detailAddress.equals(""))
        {
            Toast.makeText(this, "Please write your address...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, we are checking credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

        ValidatePhoneNumber(name, phoneNumber, password, selectedProvince, selectedDistrict, selectedCommune, detailAddress);
    }

    private void ValidatePhoneNumber(String name, String phoneNumber, String password, String province, String district, String commune, String detailAddress) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Customer").child(phoneNumber).exists())) {
                    Address address = new Address(detailAddress, province, district, commune);
                    Customer newCustomer = new Customer(phoneNumber, name, password, address);

                    RootRef.child("Customer").child(phoneNumber).updateChildren(newCustomer.toMap())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Congratulation, your account is ready now.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else {
                    Toast.makeText(RegisterActivity.this, "This " + phoneNumber + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}