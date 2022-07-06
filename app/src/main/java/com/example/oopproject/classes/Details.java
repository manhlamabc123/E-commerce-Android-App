package com.example.oopproject.classes;

public class Details {
    private double ram;
    private double memory;
    private double price;
    private String color;
    private double quantity;

    public double getRam() {
        return ram;
    }

    public double getMemory() {
        return memory;
    }

    public String getColor() {
        return color;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Details(double ram, double memory, double price, String color, double quantity) {
        this.ram = ram;
        this.memory = memory;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
    }

    public Details() {
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
