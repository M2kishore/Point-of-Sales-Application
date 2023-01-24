package com.increff.employee.service;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo inventoryPojo) throws ApiException {
        inventoryDao.insert(inventoryPojo);
    }
    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.select(id);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        return inventoryPojo;
    }
    @Transactional
    public List<InventoryPojo> getAll() {
        return inventoryDao.selectAll();
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {
        InventoryPojo oldInventoryPojo = getCheck(id);
        oldInventoryPojo.setQuantity(p.getQuantity());
        inventoryDao.update(oldInventoryPojo);
    }
}
