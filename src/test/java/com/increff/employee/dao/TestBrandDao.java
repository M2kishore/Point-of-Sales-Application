package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestBrandDao extends AbstractUnitTest {
    @Autowired
    BrandDao brandDao;

    @Test
    public void testInsert(){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandDao.insert(brandPojo);
    }

    @Test
    public void testSelect(){
        //add brand
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nike");
        brandPojo.setCategory("shoe");
        brandDao.insert(brandPojo);

        //get brand id
        int id = brandDao.selectAll().get(0).getId();
        //select brand
        BrandPojo addedBrandPojo = brandDao.select(id);
        assertEquals(brandPojo.getBrand(),addedBrandPojo.getBrand());
        assertEquals(brandPojo.getCategory(),addedBrandPojo.getCategory());
        assertEquals(id,addedBrandPojo.getId());
    }

    @Test
    public void testSelectAll(){
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("nike");
        brandPojo1.setCategory("shoe");
        brandDao.insert(brandPojo1);

        BrandPojo brandPojo2 = new BrandPojo();
        brandPojo2.setBrand("puma");
        brandPojo2.setCategory("shoe");
        brandDao.insert(brandPojo2);

        List<BrandPojo> brandPojoList = brandDao.selectAll();

        assertEquals(2,brandPojoList.size());
    }

    @Test
    public void testUpdate(){
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("nike");
        brandPojo1.setCategory("shoe");
        brandDao.insert(brandPojo1);

        BrandPojo beforeUpdateBrandPojo = brandDao.selectAll().get(0);
        beforeUpdateBrandPojo.setBrand("nikes");
        beforeUpdateBrandPojo.setCategory("shoes");

        brandDao.update(beforeUpdateBrandPojo);

        BrandPojo updatedBrandPojo = brandDao.selectAll().get(0);
        assertEquals(beforeUpdateBrandPojo.getId(),updatedBrandPojo.getId());
        assertEquals(beforeUpdateBrandPojo.getBrand(),updatedBrandPojo.getBrand());
        assertEquals(beforeUpdateBrandPojo.getCategory(),updatedBrandPojo.getCategory());
    }
}
