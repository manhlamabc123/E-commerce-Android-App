package com.example.oopproject.classes;

import com.example.oopproject.interfaces.ToMap;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends Person implements ToMap {

    private Address address;
    private ArrayList<Product> cart = new ArrayList<Product>();

    public void addProductToCart(Product product) {
        for (int i = 0; i < this.getCart().size(); i ++){
            if (this.getCart().get(i).getId().equals(product.getId())) {
                this.getCart().get(i).setQuantity(product.getDetails().get(0).getColor(),
                        this.getCart().get(i).getDetails().get(0).getQuantity() + product.getDetails().get(0).getQuantity());
                return;
            }
        }
        this.getCart().add(product);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public Address getAddress() {
        return address;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", this.getPassword());
        result.put("name", this.getName());
        result.put("phoneNumber", this.getPhoneNumber());
        result.put("cart", this.getCart());
        result.put("address", this.getAddress());

        return result;
    }

    public String getTotalPriceOfCart() {
        double totalPrice = 0;
        for (int i = 0; i < this.getCart().size(); i++) {
            totalPrice += this.getCart().get(i).getDetails().get(0).getPrice() * this.getCart().get(i).getDetails().get(0).getQuantity();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(totalPrice);
    }

    public void removeItemFromCart(int position) {
        this.getCart().remove(position);
    }

    public void removeAllProducts() {
        this.getCart().removeAll(this.getCart());
    }

    public Customer(String phoneNumber, String name, String password, Address address) {
        super(phoneNumber, name, password);
        this.address = address;
    }

    public Customer() {
    }
}
