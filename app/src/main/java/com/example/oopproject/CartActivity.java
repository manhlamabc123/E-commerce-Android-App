package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oopproject.classes_for_controll.Prevalent;
import com.example.oopproject.classes.Product;
import com.example.oopproject.classes_for_controll.CartAdapter;
import com.example.oopproject.interfaces.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private Button nextProcessBtn;
    private TextView textTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //--------------------------Connect to UI--------------------------
        recyclerView = findViewById(R.id.cart_list);
        nextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        textTotalAmount = (TextView) findViewById(R.id.total_price);
        //------------------------------------------------------------------------------

        //--------------------------Recycler View--------------------------
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CartAdapter(this, Prevalent.getCurrentCustomer().getCart(), CartActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //------------------------------------------------------------------------------

        //--------------------------Total Price Text--------------------------
        textTotalAmount.setText(Prevalent.getCurrentCustomer().getTotalPriceOfCart());
        //------------------------------------------------------------------------------

        //------------------------------Next Button--------------------------
        if (Prevalent.getCurrentCustomer().getCart().isEmpty()) nextProcessBtn.setEnabled(false);
        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------
    }

    //------------------------------Cart's item on Click------------------------------
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        //Dialog Options
        CharSequence options[] = new CharSequence[]{"Remove"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Remove from Cart?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //------------------------------if Remove is pressed------------------------------
                if (i == 0) {
                    updateProductQuantity(position, new FirebaseCallback() {
                        @Override
                        public void onCallback() {
                            //------------------------------On Server: Update User's Cart------------------------------
                            Prevalent.getCurrentCustomer().removeItemFromCart(position);
                            FirebaseDatabase.getInstance().getReference().child("Customer")
                                    .child(Prevalent.getCurrentCustomer().getPhoneNumber())
                                    .updateChildren(Prevalent.getCurrentCustomer().toMap())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                finish();
                                                startActivity(getIntent());
                                                Toast.makeText(CartActivity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            //------------------------------------------------------------------------------------------
                        }
                    });
                }
                //------------------------------------------------------------------------------------------
            }
        });
        builder.show();
        //------------------------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------------------

    private void updateProductQuantity (int position, FirebaseCallback firebaseCallback) {
        //------------------------------On Server: Retrieve Product Data------------------------------
        FirebaseDatabase.getInstance().getReference().child("Product").
                child(Prevalent.getCurrentCustomer().getCart().get(position).getId()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //------------------------------Local: Update Product Data------------------------------
                            Product product = snapshot.getValue(Product.class);
                            Product currentProduct = Prevalent.getCurrentCustomer().getCart().get(position);
                            String productColor = currentProduct.getDetails().get(0).getColor();
                            double productCurrentQuantity = product.getQuantityByColor(productColor);
                            double cartProductQuantity = currentProduct.getQuantityByColor(productColor);
                            product.setQuantity(productColor, productCurrentQuantity + cartProductQuantity);
                            //------------------------------------------------------------------------------------------

                            //------------------------------Server: Update Product Data------------------------------
                            FirebaseDatabase.getInstance().getReference().child("Product").
                                    child(Prevalent.getCurrentCustomer().getCart().get(position).getId()).
                                    updateChildren(product.toMap());
                            //------------------------------------------------------------------------------------------

                            //------------------------------Server: Update User Cart------------------------------
                            firebaseCallback.onCallback();
                            //------------------------------------------------------------------------------------------
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //------------------------------------------------------------------------------------------------------------------------
    }

    //-----------------------Interface for "Using many Firebase event listener at the same time"------------------------------
    private interface FirebaseCallback {
        void onCallback();
    }
    //------------------------------------------------------------------------------------------------------------------------
}