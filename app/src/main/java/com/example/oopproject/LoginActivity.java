package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oopproject.classes_for_controll.Prevalent;
import com.example.oopproject.classes.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Customer";

    private CheckBox chkBoxRememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputNumber = (EditText) findViewById(R.id.login_phone_number_input);
        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, we are checking credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

        AllowAccessToAccount(phone, password);
    }

    private void AllowAccessToAccount(String phone, String password) {
        if (chkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserphoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()) {
                    Customer customerData = snapshot.child(parentDbName).child(phone).getValue(Customer.class);
                    if (customerData.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        Prevalent.setCurrentCustomer(customerData);
                        startActivity(intent);
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid phone number. You may want to register!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}