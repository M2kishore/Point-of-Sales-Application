package com.increff.employee.pojo;

import javax.persistence.*;
import java.util.Date;
@Entity
public class OrderPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "orderIdSequence")
    @SequenceGenerator(name = "orderIdSequence",initialValue = 1000,allocationSize = 1,sequenceName = "orderId")
    private int id;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
