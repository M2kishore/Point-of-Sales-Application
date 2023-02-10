package com.increff.employee.service;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class BrandServiceTest extends AbstractUnitTest {
    @Autowired
    private BrandService brandService;
    @Test
    public void testAdd() throws ApiException{
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandService.add(brandPojo);
    }

    @Test
    public void testEmptyStringAdd() throws ApiException{
        try{
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("");
        brandPojo.setCategory("");
        brandService.add(brandPojo);
        }catch (ApiException e){
            assertEquals(e.getMessage(),"Brand name or Category name cannot be empty");
        }
    }

    @Test
    public void testGetSingle() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandService.add(brandPojo);
        int id = brandService.getAll().get(0).getId();
        brandPojo = brandService.get(id);
        String brand = brandPojo.getBrand();
        String category = brandPojo.getCategory();
        assertEquals("nike",brand);
        assertEquals("shoe",category);
    }

    @Test
    public void testDuplicateEntry() throws ApiException{
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("nike");
        brandPojo1.setCategory("shoe");
        brandService.add(brandPojo1);
        BrandPojo brandPojo2 = new BrandPojo();
        brandPojo2.setBrand("nike");
        brandPojo2.setCategory("shoe");
        try{
            brandService.add(brandPojo2);
        }catch (ApiException e){
            assertEquals(e.getMessage(),"Brand and Category Combination is already present");
        }
    }
    @Test
    public void testGetSingleNotPresent() throws ApiException{
        try {
            brandService.get(1);
        }catch (ApiException e){
            assertEquals("Brand with given ID does not exit, id: 1",e.getMessage());
        }

    }

    @Test
    public void testGetAll() throws ApiException{
        //add pojo
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandService.add(brandPojo);
        //get list of all pojo
        List<BrandPojo> brandPojoList = brandService.getAll();
        //check size and isEmpty()
        assertFalse(brandPojoList.isEmpty());
        assertEquals(brandPojoList.size(),1);
    }

    @Test
    public void testUpdate() throws ApiException{
        //add
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandService.add(brandPojo);
        //update
        brandPojo = brandService.getAll().get(0);
        brandPojo.setBrand("nuke");
        brandPojo.setCategory("shoes");
        brandService.update(brandPojo.getId(),brandPojo);
        //check updated pojo
        BrandPojo updatedBrandPojo = brandService.get(brandPojo.getId());
        assertEquals(updatedBrandPojo.getBrand(),"nuke");
        assertEquals(updatedBrandPojo.getCategory(),"shoes");
    }
    @Test
    public void testNormalize(){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("Nike");
        brandPojo.setCategory("Shoe");
        brandService.normalize(brandPojo);
        assertEquals(brandPojo.getBrand(),"nike");
        assertEquals(brandPojo.getCategory(),"shoe");
    }
}
