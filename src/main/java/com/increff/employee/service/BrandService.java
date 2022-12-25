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
        if(StringUtil.isEmpty(brandPojo.getBrand()) || StringUtil.isEmpty(brandPojo.getCategory())) {
            throw new ApiException("name or category cannot be empty");
        }
        brandDao.insert(brandPojo);
    }
    @Transactional
    public void delete(int id) {
        brandDao.delete(id);
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
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        BrandPojo oldBrandPojo = getCheck(id);
        oldBrandPojo.setBrand(p.getBrand());
        oldBrandPojo.setCategory(p.getCategory());
        brandDao.update(oldBrandPojo);
    }
    protected static void normalize(BrandPojo brandPojo) {
        brandPojo.setBrand(StringUtil.toLowerCase(brandPojo.getBrand()));
        brandPojo.setCategory(StringUtil.toLowerCase(brandPojo.getCategory()));
    }
}
