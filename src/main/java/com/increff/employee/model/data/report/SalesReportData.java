package com.increff.employee.model.data.report;

public class SalesReportData {
    private String brand;
    private String category;
    private int quantity;
    private double revenue;

    public SalesReportData(String brand, String category, long quantity, double revenue) {
        this.brand = brand;
        this.category = category;
        this.quantity = (int) quantity;
        this.revenue = revenue;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
