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
}
