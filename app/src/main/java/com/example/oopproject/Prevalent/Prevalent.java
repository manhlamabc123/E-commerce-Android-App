package com.example.oopproject.Prevalent;

import com.example.oopproject.classes.Customer;

public class Prevalent {
    public static Customer currentCustomer;

    public static final String UserphoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(Customer currentCustomer) {
        Prevalent.currentCustomer = currentCustomer;
    }
}
