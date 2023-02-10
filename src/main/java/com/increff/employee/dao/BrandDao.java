package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao {
    private static String select_id = "select brandPojo from BrandPojo brandPojo where id=:id";
    private static String select_all = "select brandPojo from BrandPojo brandPojo";
    private static String check_duplicate = "select brandPojo from BrandPojo brandPojo where brand=:brand and category=:category";

    @Transactional
    public void insert(BrandPojo brandPojo) {
        entityManager().persist(brandPojo);
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo brandPojo) {
    }
    public BrandPojo isDuplicate(String brand,String category){
        TypedQuery<BrandPojo> query = getQuery(check_duplicate, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }

}
