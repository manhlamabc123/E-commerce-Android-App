package com.example.oopproject.classes;

import com.example.oopproject.interfaces.ToMap;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements ToMap {
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

    public String getPrice() {
        double totalPrice = 0;
        for (int i = 0; i < this.getProducts().size(); i++) {
            totalPrice += this.getProducts().get(i).getDetails().get(0).getPrice() * this.getProducts().get(i).getDetails().get(0).getQuantity();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(totalPrice);
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(this.getBuyDate());
    }
}
