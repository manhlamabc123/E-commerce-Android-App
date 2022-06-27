<<<<<<< HEAD
package com.example.oopproject.classes;

import java.util.Date;

public class Order {
    private String id;
    private String KH_id;
    private String CN_id;
    private String NV_id;
    private Date buyDate;
    private String purchaseFrom;
}
=======
package com.example.oopproject.classes;

import java.util.Date;

public class Order {
    private String id;
    private Customer customer;
    private Agency agency;
    private Employee employee;
    private Date buyDate;
    private String purchaseFrom;
}
>>>>>>> 6e33cb3aefb5036f1de66c32d7769a4786aa7475
