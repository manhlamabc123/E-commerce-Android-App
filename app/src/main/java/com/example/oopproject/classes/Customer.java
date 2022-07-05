package com.example.oopproject.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer extends Person{
    private Date dateOfBirth;
    private String gender;
    private String DC_id;
    private String image;
    private List<Product> cart = new ArrayList<Product>();

    public String getImage() {
        return image;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDC_id() {
        return DC_id;
    }

    public void setDC_id(String DC_id) {
        this.DC_id = DC_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void addProductToCart(Product product) {
        this.cart.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", this.getPassword());
        result.put("name", this.getName());
        result.put("phone", this.getPhone());
        result.put("cart", this.getCart());

        return result;
    }
}
