package com.increff.employee.controller.api;

import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductApiController {
    @Autowired
    private ProductService productService;
    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        ProductPojo newProductPojo = convertFormToPojo(form);
        productService.add(newProductPojo);
    }
    @ApiOperation(value="Deletes a product")
    @RequestMapping(path="/api/product/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        productService.delete(id);
    }
    @ApiOperation(value = "Gets a product by ID")
    @RequestMapping(path ="/api/product/{id}",method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        return convertPojoToData(productPojo);
    }
    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo productPojo : list) {
            list2.add(convertPojoToData(productPojo));
        }
        return list2;
    }
    @ApiOperation(value = "Updates a product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm productForm) throws ApiException {
        ProductPojo updatedProductPojo = convertFormToPojo(productForm);
        productService.update(id, updatedProductPojo);
    }

    private static ProductData convertPojoToData(ProductPojo productPojo) {
        ProductData productData = new ProductData();
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrandCategory(productPojo.getBrandCategory());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        productData.setId(productPojo.getId());
        return productData;
    }
    private static ProductPojo convertFormToPojo(ProductForm productForm) {
        ProductPojo newProductPojo = new ProductPojo();
        newProductPojo.setBarcode(productForm.getBarcode());
        newProductPojo.setBrandCategory(productForm.getBrandCategory());
        newProductPojo.setName(productForm.getName());
        newProductPojo.setMrp(productForm.getMrp());
        return newProductPojo;
    }
}
