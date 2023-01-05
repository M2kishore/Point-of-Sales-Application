package com.increff.employee.model.data;

import com.increff.employee.model.form.InventoryForm;

public class InventoryData extends InventoryForm {
    private int id;

    public InventoryData(int id, int quantity) {
        super(id, quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
