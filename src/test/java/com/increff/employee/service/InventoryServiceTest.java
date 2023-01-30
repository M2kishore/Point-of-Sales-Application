package com.increff.employee.service;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class InventoryServiceTest extends AbstractUnitTest {

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testAdd() throws ApiException{
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryService.add(inventoryPojo);
    }

    @Test
    public void testGetSingle() throws ApiException{
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryService.add(inventoryPojo);
        //get pojo
        InventoryPojo addedInventoryPojo = inventoryService.get(1);
        assertEquals(addedInventoryPojo.getQuantity(),inventoryPojo.getQuantity());
    }

    @Test
    public void testGetSingleNotPresent() throws ApiException{
        try {
            inventoryService.get(1);
        }catch (ApiException e){
            assertEquals("Inventory with given ID does not exit, id: 1",e.getMessage());
        }

    }
    @Test
    public void testGetAll() throws ApiException{
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryService.add(inventoryPojo);
        //get list of all pojo
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        assertFalse(inventoryPojoList.isEmpty());
        assertEquals(inventoryPojoList.size(),1);
    }

    @Test
    public void testUpdate() throws ApiException{
        //add pojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(100);
        inventoryService.add(inventoryPojo);
        //update pojo
        inventoryPojo.setQuantity(5);
        inventoryService.update(1,inventoryPojo);
        //check updated pojo
        InventoryPojo updatedInventoryPojo = inventoryService.get(1);
        assertEquals(updatedInventoryPojo.getQuantity(),inventoryPojo.getQuantity());
    }
}
