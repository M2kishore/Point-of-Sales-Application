package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandDao brandDao;
    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo brandPojo) throws ApiException {
        normalize(brandPojo);
        checkValidity(brandPojo);
        brandDao.insert(brandPojo);
    }
    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo brandPojo = brandDao.select(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return brandPojo;
    }
    @Transactional
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo newBrandPojo) throws ApiException {
        normalize(newBrandPojo);
        checkValidity(newBrandPojo);
        BrandPojo oldBrandPojo = getCheck(id);
        oldBrandPojo.setBrand(newBrandPojo.getBrand());
        oldBrandPojo.setCategory(newBrandPojo.getCategory());
        brandDao.update(oldBrandPojo);
    }
    public static void normalize(BrandPojo brandPojo) {
        brandPojo.setBrand(StringUtil.toLowerCase(brandPojo.getBrand()));
        brandPojo.setCategory(StringUtil.toLowerCase(brandPojo.getCategory()));
    }
    private void checkValidity(BrandPojo brandPojo) throws ApiException {
        if(StringUtil.isEmpty(brandPojo.getBrand()) || StringUtil.isEmpty(brandPojo.getCategory())) {
            throw new ApiException("Brand name or Category name cannot be empty");
        }
        if(StringUtil.isLongString(brandPojo.getBrand())){
            throw new ApiException("Brand name should be less than 20 characters");
        }
        if(StringUtil.isLongString(brandPojo.getCategory())){
            throw new ApiException("Category name should be less than 20 characters");
        }
        BrandPojo duplicateBrandPojo = brandDao.isDuplicate(brandPojo.getBrand(),brandPojo.getCategory());
        if(duplicateBrandPojo != null){
            throw new ApiException("Brand and Category Combination is already present");
        }
    }
}
