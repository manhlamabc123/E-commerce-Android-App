package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.oopproject.classes.Product;
import com.example.oopproject.classes_for_controll.ProductAdapter;
import com.example.oopproject.interfaces.ItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ItemClickListener{

    private SearchView searchView;
    private ArrayList<Product> productArrayList;
    private DatabaseReference productReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ItemClickListener itemClickListener;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //-----------------On Server: get Product List-----------------
        productReference = FirebaseDatabase.getInstance().getReference().child("Product");
        //--------------------------------------------------------------------

        //-----------------Connect to UI----------------------------------
        searchView = (SearchView) findViewById(R.id.search_bar);
        recyclerView = (RecyclerView) findViewById(R.id.product_list);
        loadingBar = new ProgressDialog(this);
        //--------------------------------------------------------------------

        //-----------------Recycle View----------------------------------
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------------------------------------
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadingBar.setTitle("Search for Product");
        loadingBar.setMessage("Searching...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        itemClickListener = this;
        if (productReference != null) {
            productReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        productArrayList = new ArrayList<Product>();
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            productArrayList.add(ds.getValue(Product.class));
                        }
                        ProductAdapter adapter = new ProductAdapter(itemClickListener, productArrayList);
                        recyclerView.setAdapter(adapter);
                        loadingBar.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText, itemClickListener);
                    return true;
                }
            });
        }
    }

    private void search(String keyword, ItemClickListener itemClickListener) {
        if (!keyword.equals("")){
            ArrayList<Product> productSearchList = new ArrayList<>();
            for (Product object : productArrayList){
                if(object.getName().toLowerCase().contains(keyword.toLowerCase())){
                    productSearchList.add(object);
                }
            }
            ProductAdapter adapterClass = new ProductAdapter(itemClickListener, productSearchList);
            recyclerView.setAdapter(adapterClass);
        }
        else {
            ProductAdapter adapterClass = new ProductAdapter(itemClickListener, productArrayList);
            recyclerView.setAdapter(adapterClass);
        }
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }
}