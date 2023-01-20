package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
public class OrderItemPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "orderItemIdSequence")
    @SequenceGenerator(name = "orderItemIdSequence",initialValue = 100000,allocationSize = 1,sequenceName = "orderItemId")
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
