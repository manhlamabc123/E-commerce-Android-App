package com.example.oopproject.classes;

import java.util.HashMap;
import java.util.Map;

public class Address {
    private String number;
    private String province;
    private String district;
    private String wards;

    public Address(String number, String province, String district, String wards) {
        this.number = number;
        this.province = province;
        this.district = district;
        this.wards = wards;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getWards() {
        return wards;
    }

    public String getNumber() {
        return number;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("number", this.getNumber());
        result.put("province", this.getProvince());
        result.put("district", this.getDistrict());
        result.put("wards", this.getWards());
        return result;
    }

    public String getAddress() {
        return number + ", " + province + ", " + district + ", " + wards;
    }

    public Address() {
    }
}
