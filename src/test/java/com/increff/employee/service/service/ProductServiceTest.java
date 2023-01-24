package com.increff.employee.service.service;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends AbstractUnitTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAdd() throws ApiException{
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productService.add(productPojo);
    }

    @Test
    public void testGetSingle() throws ApiException{
        //add product
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productService.add(productPojo);
        //checking added product
        ProductPojo addedProductPojo = productService.get(4);
        assertEquals(addedProductPojo.getBarcode(),productPojo.getBarcode());
        assertEquals(addedProductPojo.getMrp(),productPojo.getMrp(),0.01);
        assertEquals(addedProductPojo.getName(),productPojo.getName());
        assertEquals(addedProductPojo.getBrandCategory(),productPojo.getBrandCategory());
    }

    @Test
    public void testGetAll() throws ApiException{
        //add pojo
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productService.add(productPojo);
        //get list of all pojo
        List<ProductPojo> productPojoList = productService.getAll();
        //check size and isEmpty()
        assertFalse(productPojoList.isEmpty());
        assertEquals(productPojoList.size(),1);
    }

    @Test
    public void testUpdate() throws ApiException{
        //add pojo
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productService.add(productPojo);
        //update pojo
        productPojo.setName("sparkles");
        productPojo.setMrp(150);
        productService.update(5,productPojo);
        //check updated pojo
        ProductPojo updatedProductPojo = productService.get(5);
        assertEquals(updatedProductPojo.getName(),productPojo.getName());
        assertEquals(updatedProductPojo.getBarcode(),productPojo.getBarcode());
        assertEquals(updatedProductPojo.getMrp(),productPojo.getMrp(),0.01);
        assertEquals(updatedProductPojo.getBrandCategory(),productPojo.getBrandCategory());
    }

    @Test
    public void testBarcode() throws ApiException{
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("sparkle");
        productPojo.setBarcode("qwe123");
        productPojo.setBrandCategory(1);
        productPojo.setMrp(100.25);
        productService.add(productPojo);
        //check barcode
        ProductPojo addedProductPojo = productService.getBarcode("qwe123");
        assertEquals(addedProductPojo.getName(),productPojo.getName());
        assertEquals(addedProductPojo.getBarcode(),productPojo.getBarcode());
        assertEquals(addedProductPojo.getMrp(),productPojo.getMrp(),0.01);
        assertEquals(addedProductPojo.getBrandCategory(),productPojo.getBrandCategory());

    }

}
