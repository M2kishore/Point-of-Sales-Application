package com.increff.employee.model.form;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement
public class BillFormList {

    private List<BillForm> billForm;
    private double total;
    private int orderId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<BillForm> getBillForm() {
        return billForm;
    }

    public void setBillForm(List<BillForm> billForm) {
        this.billForm = billForm;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

