package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.oopproject.classes.Employee;
import com.example.oopproject.classes_for_controll.EmployeeAdapter;
import com.example.oopproject.interfaces.ItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity implements ItemClickListener{

    private SearchView searchView;
    private ArrayList<Employee> employeeArrayList;
    private DatabaseReference employeeReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        searchView = (SearchView) findViewById(R.id.search_bar);
        recyclerView = (RecyclerView) findViewById(R.id.employee_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        employeeReference = FirebaseDatabase.getInstance().getReference().child("Employee");
    }

    @Override
    protected void onStart() {
        super.onStart();

        itemClickListener = this;
        if (employeeReference != null) {
            employeeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        employeeArrayList = new ArrayList<Employee>();
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            employeeArrayList.add(ds.getValue(Employee.class));
                        }
                        EmployeeAdapter adapter = new EmployeeAdapter(itemClickListener, employeeArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EmployeeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            ArrayList<Employee> employeeSearchList = new ArrayList<>();
            for (Employee object : employeeArrayList){
                if(object.getName().toLowerCase().contains(keyword.toLowerCase())){
                    employeeSearchList.add(object);
                }
            }
            EmployeeAdapter adapterClass = new EmployeeAdapter(itemClickListener, employeeSearchList);
            recyclerView.setAdapter(adapterClass);
        }
        else {
            EmployeeAdapter adapterClass = new EmployeeAdapter(itemClickListener, employeeArrayList);
            recyclerView.setAdapter(adapterClass);
        }
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }
}