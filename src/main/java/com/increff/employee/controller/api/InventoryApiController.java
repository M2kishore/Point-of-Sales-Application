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
@RequestMapping(path = "/api/inventory")
public class InventoryApiController {
    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Add a inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    protected void add(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo newInventoryPojo = convertFormToPojo(form);
        //check if inventory is already present
        InventoryPojo checkInventoryPojo = inventoryService.getCheck(newInventoryPojo.getId());
        if(checkInventoryPojo == null){
            inventoryService.add(newInventoryPojo);
            return;
        }
        throw new ApiException("Inventory already present try updating it");
    }
    @ApiOperation(value = "Gets a inventory by id")
    @RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        return convertPojoToData(inventoryPojo);
    }
    @ApiOperation(value = "Gets list of all inventory")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<InventoryData> getAll() {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : list) {
            list2.add(convertPojoToData(inventoryPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Updates a inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventoryForm) throws ApiException {
        InventoryPojo updatedInventoryPojo = convertFormToPojo(inventoryForm);
        inventoryService.update(id, updatedInventoryPojo);
    }

    private static InventoryData convertPojoToData(InventoryPojo inventoryPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        return inventoryData;
    }
    private static InventoryPojo convertFormToPojo(InventoryForm inventoryForm) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(inventoryForm.getId());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }
}
