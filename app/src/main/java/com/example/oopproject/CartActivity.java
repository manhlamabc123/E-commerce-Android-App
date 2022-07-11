package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oopproject.Prevalent.Prevalent;
import com.example.oopproject.classes.Product;
import com.example.oopproject.classes_for_controll.CartAdapter;
import com.example.oopproject.interfaces.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.paperdb.Paper;

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
        adapter = new CartAdapter(this, Prevalent.currentCustomer.getCart(), CartActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //------------------------------------------------------------------------------

        //--------------------------Total Price Text--------------------------
        textTotalAmount.setText(Prevalent.currentCustomer.getTotalPriceOfCart());
        //------------------------------------------------------------------------------

        //------------------------------Next Button--------------------------
        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        CharSequence options[] = new CharSequence[]{"Remove"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Remove from Cart?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    readProduct(position, new FirebaseCallback() {
                        @Override
                        public void onCallback() {
                            Prevalent.currentCustomer.removeItemFromCart(position);
                            FirebaseDatabase.getInstance().getReference().child("Customer")
                                    .child(Prevalent.currentCustomer.getPhone())
                                    .updateChildren(Prevalent.currentCustomer.toMap())
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
                        }
                    });
                }
            }
        });
        builder.show();
    }

    private void readProduct (int position, FirebaseCallback firebaseCallback) {
        FirebaseDatabase.getInstance().getReference().child("Product").
                child(Prevalent.currentCustomer.getCart().get(position).getId()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Product product = snapshot.getValue(Product.class);
                            Product currentProduct = Prevalent.currentCustomer.getCart().get(position);
                            String productColor = currentProduct.getDetails().get(0).getColor();
                            double productCurrentQuantity = product.getQuantityByColor(productColor);
                            double cartProductQuantity = currentProduct.getQuantityByColor(productColor);
                            product.setQuantity(productColor, productCurrentQuantity + cartProductQuantity);

                            FirebaseDatabase.getInstance().getReference().child("Product").
                                    child(Prevalent.currentCustomer.getCart().get(position).getId()).
                                    updateChildren(product.toMap());
                            firebaseCallback.onCallback();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private interface FirebaseCallback {
        void onCallback();
    }
}