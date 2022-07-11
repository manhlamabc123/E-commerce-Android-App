package com.example.oopproject.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private String id;
    private String name;
    private String category;
    private String manufacturer;
    private String include;
    private String os;
    private double screen;
    private String description;
    private double warranty;
    private ArrayList<Details> details;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getInclude() {
        return include;
    }

    public String getOs() {
        return os;
    }

    public double getScreen() {
        return screen;
    }

    public String getDescription() {
        return description;
    }

    public double getWarranty() {
        return warranty;
    }

    public ArrayList<Details> getDetails() {
        return details;
    }

    public String getId() {
        return id;
    }

    public Product(String id, String name, String category, String manufacturer, String include, String os, double screen, String description, double warranty, ArrayList<Details> details) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
        this.include = include;
        this.os = os;
        this.screen = screen;
        this.description = description;
        this.warranty = warranty;
        this.details = details;
    }

    public Product() {
    }

    public void setQuantity(String color, double quantity) {
        for (int i = 0; i < this.getDetails().size(); i++) {
            if (this.getDetails().get(i).getColor().equals(color)) {
                this.getDetails().get(i).setQuantity(quantity);
            }
        }
    }

    public Map<String, Object> toMap() {
        HashMap<java.lang.String, java.lang.Object> result = new HashMap<>();

        result.put("category", this.getCategory());
        result.put("description", this.getDescription());
        result.put("details", this.getDetails());
        result.put("id", this.getId());
        result.put("include", this.getInclude());
        result.put("manufacturer", this.getManufacturer());
        result.put("name", this.getName());
        result.put("os", this.getOs());
        result.put("screen", this.getScreen());
        result.put("warranty", this.getWarranty());

        return result;
    }

    public double getQuantityByColor(String color) {
        for (int i = 0; i < this.getDetails().size(); i++) {
            if (this.getDetails().get(i).getColor().equals(color)) {
                return this.getDetails().get(i).getQuantity();
            }
        }
        return 0;
    }
}
