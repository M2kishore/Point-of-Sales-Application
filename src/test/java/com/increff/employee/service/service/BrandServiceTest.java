package com.increff.employee.service.service;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
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
            assertEquals(e.getMessage(),"name or category cannot be empty");
        }
    }

    @Test
    public void testGetSingle() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandService.add(brandPojo);
        brandPojo = brandService.get(3);
        String brand = brandPojo.getBrand();
        String category = brandPojo.getCategory();
        System.out.println(brand+" "+category);
        assertEquals("nike",brand);
        assertEquals("shoe",category);
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
        brandPojo.setBrand("nuke");
        brandPojo.setCategory("shoes");
        brandService.update(4,brandPojo);
        //check updated pojo
        BrandPojo updatedBrandPojo = brandService.get(4);
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
