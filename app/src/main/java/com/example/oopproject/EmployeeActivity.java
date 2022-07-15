package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
    private Spinner jobFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        //-----------------On Server: get Employee List-----------------
        employeeReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        //--------------------------------------------------------------------

        //-----------------Connect to UI----------------------------------
        searchView = (SearchView) findViewById(R.id.search_bar);
        recyclerView = (RecyclerView) findViewById(R.id.employee_list);
        jobFilter = (Spinner) findViewById(R.id.employee_job_filter);
        //--------------------------------------------------------------------

        //-----------------Recycle View----------------------------------
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------------------------------------

        //----------------------------------Job Filter---------------------------------------------------
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EmployeeActivity.this, R.array.employee_job_list, R.layout.employee_job_snipper_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobFilter.setAdapter(adapter);
        jobFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (recyclerView.getAdapter() == null) return;
                searchView.setQuery("", false);
                searchView.clearFocus();
                search("", jobFilter.getSelectedItem().toString(), itemClickListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //------------------------------------------------------------------------------------------------------
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
                    String job = jobFilter.getSelectedItem().toString();
                    search(newText, job, itemClickListener);
                    return true;
                }
            });
        }
    }

    private void search(String keyword, String job, ItemClickListener itemClickListener) {
        ArrayList<Employee> employeeSearchList = new ArrayList<>();
        if (!keyword.equals("")){
            for (Employee object : employeeArrayList){
                if(object.getName().toLowerCase().contains(keyword.toLowerCase()) && object.getJob().equals(job)){
                    employeeSearchList.add(object);
                }
            }
            EmployeeAdapter adapterClass = new EmployeeAdapter(itemClickListener, employeeSearchList);
            recyclerView.setAdapter(adapterClass);
        }
        else {
            for (Employee object : employeeArrayList){
                if(object.getJob().equals(job)){
                    employeeSearchList.add(object);
                }
            }
            EmployeeAdapter adapterClass = new EmployeeAdapter(itemClickListener, employeeSearchList);
            recyclerView.setAdapter(adapterClass);
        }
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        CharSequence option[] = new CharSequence[]{"Call"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeActivity.this);
        builder.setTitle("Call?");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    String employeePhoneNumber =  employeeArrayList.get(position).getPhoneNumber();
                    String phoneFormat = "tel:" + employeePhoneNumber;

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(phoneFormat));
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }
}