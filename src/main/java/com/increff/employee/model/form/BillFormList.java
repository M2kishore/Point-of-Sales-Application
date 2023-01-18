package com.increff.employee.model.form;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement
public class BillFormList {

    private List<BillForm> billForm;
    private double total;

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

