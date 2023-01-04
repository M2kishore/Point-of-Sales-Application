package com.increff.employee.controller.api;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryApiController {
    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Add a inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    protected void add(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo newInventoryPojo = convertFormToPojo(form);
        inventoryService.add(newInventoryPojo);
    }
    @ApiOperation(value="Deletes a inventory")
    @RequestMapping(path="/api/inventory/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        inventoryService.delete(id);
    }
    @ApiOperation(value = "Gets a inventory by ID")
    @RequestMapping(path ="/api/inventory/{id}",method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        return convertPojoToData(inventoryPojo);
    }
    @ApiOperation(value = "Gets list of all inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : list) {
            list2.add(convertPojoToData(inventoryPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Updates a inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventoryForm) throws ApiException {
        InventoryPojo updatedInventoryPojo = convertFormToPojo(inventoryForm);
        inventoryService.update(id, updatedInventoryPojo);
    }

    private static InventoryData convertPojoToData(InventoryPojo inventoryPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setId(inventoryPojo.getId());
        return inventoryData;
    }
    private static InventoryPojo convertFormToPojo(InventoryForm inventoryForm) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }
}
