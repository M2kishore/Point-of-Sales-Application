package com.increff.pos.controller.api;

import com.increff.pos.model.data.BrandData;
import com.increff.pos.model.form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class BrandApiController {
    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "Add a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        BrandPojo newBrandPojo = convertFormToPojo(form);
        brandService.add(newBrandPojo);
    }
    @ApiOperation(value="Deletes a brand")
    @RequestMapping(path="/api/brand/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        brandService.delete(id);
    }
    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path ="/api/brand/{id}",method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo brandPojo = brandService.get(id);
        return convertPojoToData(brandPojo);
    }
    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo brandPojo : list) {
            list2.add(convertPojoToData(brandPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        BrandPojo updatedBrandPojo = convertFormToPojo(brandForm);
        brandService.update(id, updatedBrandPojo);
    }

    private static BrandData convertPojoToData(BrandPojo p) {
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }
    private static BrandPojo convertFormToPojo(BrandForm brandForm) {
        BrandPojo newBrandPojo = new BrandPojo();
        newBrandPojo.setBrand(brandForm.getBrand());
        newBrandPojo.setCategory(brandForm.getCategory());
        return newBrandPojo;
    }
}
