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
    @RequestMapping(path="/api/inventory/{sno}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int sno){
        inventoryService.delete(sno);
    }
    @ApiOperation(value = "Gets a inventory by Sno")
    @RequestMapping(path ="/api/inventory/{sno}",method = RequestMethod.GET)
    public InventoryData get(@PathVariable int sno) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(sno);
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
    @RequestMapping(path = "/api/inventory/{sno}", method = RequestMethod.PUT)
    public void update(@PathVariable int sno, @RequestBody InventoryForm inventoryForm) throws ApiException {
        InventoryPojo updatedInventoryPojo = convertFormToPojo(inventoryForm);
        inventoryService.update(sno, updatedInventoryPojo);
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
