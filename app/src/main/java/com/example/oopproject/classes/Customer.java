package com.example.oopproject.classes;

import java.util.Date;

public class Customer extends Person{
    private Date dateOfBirth;
    private String gender;
    private String DC_id;
    private String image;

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
}
