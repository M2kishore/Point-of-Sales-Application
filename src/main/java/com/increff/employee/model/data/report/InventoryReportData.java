package com.increff.employee.model.data.report;

public class InventoryReportData {
    private String brand;
    private String category;
    private int quantity;

    public InventoryReportData(String brand, String category, long quantity) {
        this.brand = brand;
        this.category = category;
        this.quantity = (int) quantity;
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
}
