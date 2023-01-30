package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestProductDao extends AbstractUnitTest {
    @Autowired
    ProductDao productDao;
    @Test
    public void testInsert(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productDao.insert(productPojo);
    }

    @Test
    public void testSelect(){
        //add product
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productDao.insert(productPojo);
        //get id of addded product
        int id = productDao.selectAll().get(0).getId();

        ProductPojo addedProductPojo = productDao.select(id);
        assertEquals(addedProductPojo.getBarcode(),productPojo.getBarcode());
        assertEquals(addedProductPojo.getMrp(),productPojo.getMrp(),0.01);
        assertEquals(addedProductPojo.getName(),productPojo.getName());
        assertEquals(addedProductPojo.getBrandCategory(),productPojo.getBrandCategory());
    }

    @Test
    public void testSelectBarcode(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productDao.insert(productPojo);
        //check barcode
        ProductPojo addedProductPojo = productDao.selectBarcode("qwe123");
        assertEquals(addedProductPojo.getName(),productPojo.getName());
        assertEquals(addedProductPojo.getBarcode(),productPojo.getBarcode());
        assertEquals(addedProductPojo.getMrp(),productPojo.getMrp(),0.01);
        assertEquals(addedProductPojo.getBrandCategory(),productPojo.getBrandCategory());
    }
    @Test
    public void testSelectAll(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productDao.insert(productPojo);
        //get list of all pojo
        List<ProductPojo> productPojoList = productDao.selectAll();
        //check size and isEmpty()
        assertFalse(productPojoList.isEmpty());
        assertEquals(productPojoList.size(),1);
    }

    @Test
    public void testUpdate(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productDao.insert(productPojo);
        //update pojo
        int id = productDao.selectAll().get(0).getId();
        ProductPojo beforeUpdatePojo = productDao.select(id);
        beforeUpdatePojo.setName("sparkles");
        beforeUpdatePojo.setMrp(150);
        productDao.update(beforeUpdatePojo);
        //check updated pojo
        ProductPojo updatedProductPojo = productDao.select(id);
        assertEquals(updatedProductPojo.getName(),beforeUpdatePojo.getName());
        assertEquals(updatedProductPojo.getBarcode(),beforeUpdatePojo.getBarcode());
        assertEquals(updatedProductPojo.getMrp(),beforeUpdatePojo.getMrp(),0.01);
        assertEquals(updatedProductPojo.getBrandCategory(),beforeUpdatePojo.getBrandCategory());
    }
}
