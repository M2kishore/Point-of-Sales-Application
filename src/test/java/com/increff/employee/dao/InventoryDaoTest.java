package com.increff.employee.dao;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class InventoryDaoTest extends AbstractUnitTest {
    @Autowired
    InventoryDao inventoryDao;
    @Test
    public void testInsert(){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryDao.insert(inventoryPojo);
    }

    @Test
    public void testSelect(){
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryDao.insert(inventoryPojo);
        //get pojo
        InventoryPojo addedInventoryPojo = inventoryDao.select(1);
        assertEquals(addedInventoryPojo.getQuantity(),inventoryPojo.getQuantity());
    }
    @Test
    public void testSelectAll(){
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryDao.insert(inventoryPojo);
        //get list of all pojo
        List<InventoryPojo> inventoryPojoList = inventoryDao.selectAll();
        assertFalse(inventoryPojoList.isEmpty());
        assertEquals(inventoryPojoList.size(),1);
    }
    @Test
    public void testUpdate(){
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryDao.insert(inventoryPojo);
        //update pojo
        InventoryPojo beforeUpdateInventoryPojo = inventoryDao.selectAll().get(0);
        inventoryPojo.setQuantity(5);
        inventoryDao.update(inventoryPojo);
        //check updated pojo
        InventoryPojo updatedInventoryPojo = inventoryDao.selectAll().get(0);
        assertEquals(beforeUpdateInventoryPojo.getQuantity(),updatedInventoryPojo.getQuantity());
    }
}
