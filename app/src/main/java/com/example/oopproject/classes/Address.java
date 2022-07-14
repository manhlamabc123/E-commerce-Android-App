package com.example.oopproject.classes;

import java.util.HashMap;
import java.util.Map;

public class Address {
    private String detailAddress;
    private String province;
    private String district;
    private String commune;

    public Address(String detailAddress, String province, String district, String commune) {
        this.detailAddress = detailAddress;
        this.province = province;
        this.district = district;
        this.commune = commune;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getCommune() {
        return commune;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String returnFullAddress() {
        return this.getDetailAddress() + ", " + this.getCommune() + ", " + this.getDistrict() + ", " + this.getProvince();
    }

    public Address() {
    }
}