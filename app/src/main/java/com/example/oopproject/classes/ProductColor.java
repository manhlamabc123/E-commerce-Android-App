<<<<<<< HEAD
package com.example.oopproject.classes;

public class ProductColor {
    private String id;
    private String phone_id;
    private String color_id;
    private double priceBuy;

    public String getId() {
        return id;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public String getColor_id() {
        return color_id;
    }

    public double getPriceBuy() {
        return priceBuy;
    }
}
=======
package com.example.oopproject.classes;

public class ProductColor {
    private String id;
    private Product product;
    private Color color;
    private double priceBuy;

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Color getColor() {
        return color;
    }

    public double getPriceBuy() {
        return priceBuy;
    }
}
>>>>>>> 6e33cb3aefb5036f1de66c32d7769a4786aa7475
