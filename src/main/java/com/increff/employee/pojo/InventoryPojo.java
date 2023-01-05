package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class InventoryPojo{
    @Id
//    @ManyToOne(targetEntity = ProductPojo.class)
//    @JoinColumn(referencedColumnName = "id")
    private int id;
    private int quantity;
    public InventoryPojo(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
    public InventoryPojo(int id){
        this.id = id;
        this.quantity = 0;
    }

    public InventoryPojo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
