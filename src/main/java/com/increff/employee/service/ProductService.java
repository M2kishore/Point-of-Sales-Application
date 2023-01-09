package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
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
    public void add(ProductPojo newProductPojo) throws ApiException {
        normalize(newProductPojo);
        if(StringUtil.isEmpty(newProductPojo.getBarcode()) ||
                StringUtil.isEmpty(String.valueOf(newProductPojo.getBrandCategory())) ||
                StringUtil.isEmpty(newProductPojo.getName())||
                StringUtil.isEmpty(String.valueOf(newProductPojo.getMrp()))) {
            throw new ApiException("empty string detected cannot be empty");
        }
        productDao.insert(newProductPojo);
    }
    @Transactional
    public void delete(int id) {
        productDao.delete(id);
    }
    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo productPojo = productDao.select(id);
        if (productPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
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
        oldProductPojo.setBarcode(newProductPojo.getBarcode());
        oldProductPojo.setBrandCategory(newProductPojo.getBrandCategory());
        oldProductPojo.setName(newProductPojo.getName());
        oldProductPojo.setMrp(newProductPojo.getMrp());
        productDao.update(oldProductPojo);
    }
    protected static void normalize(ProductPojo productPojo) {
        productPojo.setBarcode(StringUtil.toLowerCase(productPojo.getBarcode()));
        productPojo.setName(StringUtil.toLowerCase(productPojo.getName()));
        productPojo.setName(StringUtil.toLowerCase(productPojo.getName()));
    }
}
