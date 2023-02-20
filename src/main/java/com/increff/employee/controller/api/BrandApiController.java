package com.increff.employee.controller.api;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/brand")
public class BrandApiController {
    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "Add a brand")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        BrandPojo newBrandPojo = convertFormToPojo(form);
        brandService.add(newBrandPojo);
    }
    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo brandPojo = brandService.get(id);
        return convertPojoToData(brandPojo);
    }
    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo brandPojo : list) {
            list2.add(convertPojoToData(brandPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        BrandPojo updatedBrandPojo = convertFormToPojo(brandForm);
        brandService.update(id, updatedBrandPojo);
    }

    private static BrandData convertPojoToData(BrandPojo brandPojo) {
        BrandData brandData = new BrandData();
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandPojo.getCategory());
        brandData.setId(brandPojo.getId());
        return brandData;
    }
    private static BrandPojo convertFormToPojo(BrandForm brandForm) {
        BrandPojo newBrandPojo = new BrandPojo();
        newBrandPojo.setBrand(brandForm.getBrand());
        newBrandPojo.setCategory(brandForm.getCategory());
        return newBrandPojo;
    }
}
