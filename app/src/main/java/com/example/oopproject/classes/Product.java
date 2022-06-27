package com.example.oopproject.classes;

import java.util.ArrayList;

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
}
