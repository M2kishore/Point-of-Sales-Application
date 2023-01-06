package com.increff.employee.pojo;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class InventoryPojo{
    //    @ManyToOne(targetEntity = ProductPojo.class)
    //    @JoinColumn(referencedColumnName = "id"
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int sno;
    private int id;
    @Column()
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
        this.id = -1;
        this.quantity = 0;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
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
