package com.example.oopproject.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private Employee employee;
    private Date buyDate;
    private List<Product> products = new ArrayList<Product>();
}
