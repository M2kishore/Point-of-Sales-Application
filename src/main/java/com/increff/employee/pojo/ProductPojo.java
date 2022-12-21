package com.increff.employee.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProductPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String barcode;
    private int brand_category;
    private String name;
    private double mrp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(int brand_category) {
        this.brand_category = brand_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }
}
