package com.example.oopproject.classes;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private String id;
    private String customerId;
    private Date buyDate;
    private Address address;
    private List<Product> products = new ArrayList<Product>();

    public Order() {
    }

    public Order(String id, String customerId, Date buyDate, Address address, List<Product> products) {
        this.id = id;
        this.customerId = customerId;
        this.buyDate = buyDate;
        this.address = address;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public Address getAddress() {
        return address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", this.getId());
        result.put("customerId", this.getCustomerId());
        result.put("buyDate", this.getBuyDate());
        result.put("address", this.getAddress());
        result.put("products", this.getProducts());

        return result;
    }
}
