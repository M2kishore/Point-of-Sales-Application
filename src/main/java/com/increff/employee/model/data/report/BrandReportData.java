package com.increff.employee.model.data.report;

public class BrandReportData {
    private String brand;
    private int quantity;
    private double revenue;
    public BrandReportData(String brand, long quantity, double revenue) {
        this.brand = brand;
        this.quantity =(int) quantity;
        this.revenue = revenue;
    }
    public BrandReportData(){

    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
