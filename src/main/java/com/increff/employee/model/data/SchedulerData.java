package com.increff.employee.model.data;

import java.util.Date;

public class SchedulerData {
    private Date date;
    private int count;
    private int quantity;
    private double revenue;

    public SchedulerData(Date date, int count, int quantity, double revenue) {
        this.date = date;
        this.count = count;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
