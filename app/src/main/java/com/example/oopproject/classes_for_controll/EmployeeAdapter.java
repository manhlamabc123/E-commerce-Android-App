package com.example.oopproject.classes_for_controll;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.classes.Employee;
import com.example.oopproject.interfaces.ItemClickListener;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private final ItemClickListener itemClickListener;
    private ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(ItemClickListener itemClickListener, ArrayList<Employee> employeeArrayList) {
        this.itemClickListener = itemClickListener;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_layout, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
        holder.employeeJob.setText(employeeArrayList.get(position).getJob());
        holder.employeeName.setText(employeeArrayList.get(position).getName());
        holder.employeePhoneNumber.setText(employeeArrayList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView employeeName;
        public TextView employeeJob;
        public TextView employeePhoneNumber;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            employeeName = (TextView) itemView.findViewById(R.id.employee_name);
            employeeJob = (TextView) itemView.findViewById(R.id.employee_job);
            employeePhoneNumber = (TextView) itemView.findViewById(R.id.employee_phone_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            itemClickListener.onClick(view, pos, false);
                        }
                    }
                }
            });
        }
    }
}
