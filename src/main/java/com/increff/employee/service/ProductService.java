package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.NumberUtil;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Transactional(rollbackOn = ApiException.class)
    public int add(ProductPojo newProductPojo) throws ApiException {
        normalize(newProductPojo);
        if (StringUtil.isEmpty(newProductPojo.getBarcode())){
            throw new ApiException("Barcode cannot be empty");
        }
        if (StringUtil.isEmpty(String.valueOf(newProductPojo.getBrandCategory()))){
            throw new ApiException("Brand Category cannot be empty");
        }
        if (StringUtil.isEmpty(newProductPojo.getName())){
            throw new ApiException("Product name cannot be empty");
        }
        if (StringUtil.isEmpty(String.valueOf(newProductPojo.getMrp()))){
            throw new ApiException("Mrp cannot be empty");
        }
        if (StringUtil.hasSpecialCharacter(newProductPojo.getBarcode())){
            throw new ApiException("Special character in Barcode is not allowed");
        }
        if (StringUtil.hasSpecialCharacter(newProductPojo.getName())){
            throw new ApiException("Special character in Product name is not allowed");
        }
        if(StringUtil.isLongString(newProductPojo.getName())){
            throw new ApiException("Product name should be less than 20 characters");
        }
        if(StringUtil.isLongString(newProductPojo.getBarcode())){
            throw new ApiException("Barcode name should be less than 20 characters");
        }
        if (NumberUtil.isNegative(newProductPojo.getMrp())){
            throw new ApiException("Mrp cannot be negative");
        }
        if(!NumberUtil.has2DecimalPlaces(newProductPojo.getMrp())){
            throw new ApiException("Mrp should have only 2 numbers after decimal point");
        }
        try {
            return productDao.insert(newProductPojo);
        } catch (Exception e) {
            throw new ApiException("Duplicate barcode");
        }
    }
    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo getBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectBarcode(barcode);
        if (productPojo == null) {
            throw new ApiException("Product with given Barcode does not exit, barcode: " + barcode);
        }
        return productPojo;
    }
    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo productPojo = productDao.select(id);
        if (productPojo == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return productPojo;
    }
    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, ProductPojo newProductPojo) throws ApiException {
        normalize(newProductPojo);
        ProductPojo oldProductPojo = getCheck(id);
        if (StringUtil.isEmpty(newProductPojo.getName())){
            throw new ApiException("Product name cannot be empty");
        }
        if (StringUtil.isEmpty(String.valueOf(newProductPojo.getMrp()))){
            throw new ApiException("Mrp cannot be empty");
        }
        if(StringUtil.isLongString(newProductPojo.getName())){
            throw new ApiException("Product name should be less than 20 characters");
        }
        if(NumberUtil.isNegative(newProductPojo.getMrp())){
            throw new ApiException("Mrp cannot be negative");
        }
        if(!NumberUtil.has2DecimalPlaces(newProductPojo.getMrp())){
            throw new ApiException("Mrp should have only 2 numbers after decimal point");
        }
        oldProductPojo.setName(newProductPojo.getName());
        oldProductPojo.setMrp(newProductPojo.getMrp());
        productDao.update(oldProductPojo);
    }
    protected static void normalize(ProductPojo productPojo) {
        productPojo.setBarcode(StringUtil.toLowerCase(productPojo.getBarcode()));
        productPojo.setName(StringUtil.toLowerCase(productPojo.getName()));
    }
}
