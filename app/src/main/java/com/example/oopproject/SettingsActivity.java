package com.example.oopproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oopproject.classes.Address;
import com.example.oopproject.classes.Customer;
import com.example.oopproject.classes_for_controll.Prevalent;
import com.example.oopproject.classes_for_controll.SelectAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity
{
    private EditText fullNameEditText, detailAddressEditText, passwordEditText, confirmPasswordEditText;
    private TextView closeTextBtn, saveTextButton;
    private Button securityQuestionButton;
    private String selectedProvince, selectedDistrict, selectedCommune;
    private Spinner provinceSpinner, districtSpinner, communeSpinner;
    SelectAddress selectAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fullNameEditText = (EditText) findViewById(R.id.settings_username_input);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update_account_settings_btn);
        securityQuestionButton = (Button) findViewById(R.id.security_questions_btn);
        provinceSpinner = (Spinner)findViewById(R.id.settings_spinner_province);
        districtSpinner = (Spinner)findViewById(R.id.settings_spinner_district);
        communeSpinner = (Spinner)findViewById(R.id.settings_spinner_commune);
        detailAddressEditText = (EditText)findViewById(R.id.settings_detail_address_input);
        passwordEditText = (EditText)findViewById(R.id.settings_password_input);
        confirmPasswordEditText = (EditText)findViewById(R.id.settings_password_input_again);

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(Prevalent.getCurrentCustomer().getPhoneNumber());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    selectedProvince = dataSnapshot.child("address").child("province").getValue().toString();
                    selectedDistrict = dataSnapshot.child("address").child("district").getValue().toString();
                    selectedCommune = dataSnapshot.child("address").child("commune").getValue().toString();
                    String detailAddress = dataSnapshot.child("address").child("detailAddress").getValue().toString();

                    fullNameEditText.setText(name);
                    passwordEditText.setText(password);
                    confirmPasswordEditText.setText(password);
                    detailAddressEditText.setText(detailAddress);

                    selectAddress = new SelectAddress(SettingsActivity.this, provinceSpinner, districtSpinner, communeSpinner, selectedProvince, selectedDistrict, selectedCommune);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        securityQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "settings");
                startActivity(intent);
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = fullNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String detailAddress = detailAddressEditText.getText().toString();

                if (name.equals("") || password.equals("") || confirmPassword.equals("") || detailAddress.equals(""))
                {
                    Toast.makeText(SettingsActivity.this, "Fill all boxes", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SettingsActivity.this, "Confirm password not matching", Toast.LENGTH_SHORT).show();
                }
                else
                    updateOnlyUserInfo();
            }
        });
    }



    private void updateOnlyUserInfo()
    {
        selectedProvince = selectAddress.getSelectedProvince();
        selectedDistrict = selectAddress.getSelectedDistrict();
        selectedCommune = selectAddress.getSelectedCommune();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Customer");

        Address address = new Address(detailAddressEditText.getText().toString(),
                selectedProvince,
                selectedDistrict,
                selectedCommune);
        Customer newCustomer = new Customer(Prevalent.getCurrentCustomer().getPhoneNumber(),
                fullNameEditText.getText().toString(),
                passwordEditText.getText().toString(), address);
        ref.child(Prevalent.getCurrentCustomer().getPhoneNumber()).updateChildren(newCustomer.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Data updated succesfully", Toast.LENGTH_SHORT).show();
                            Prevalent.setCurrentCustomer(newCustomer);
                            Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SettingsActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        finish();
    }


}