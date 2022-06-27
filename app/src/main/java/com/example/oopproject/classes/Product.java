<<<<<<< HEAD
package com.example.oopproject.classes;

public class Product {
    private String id;
    private String name;
    private String category;
    private double priceImport;
    private String manufacturer;
    private String os;
    private String include;
    private double ram;
    private double memory;
    private double screen;
    private String description;
    private double warranty;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPriceImport() {
        return priceImport;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getOs() {
        return os;
    }

    public String getInclude() {
        return include;
    }

    public double getRam() {
        return ram;
    }

    public double getMemory() {
        return memory;
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

    public double getPrice() {
        return 0;
    }
}
=======
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
>>>>>>> 6e33cb3aefb5036f1de66c32d7769a4786aa7475
