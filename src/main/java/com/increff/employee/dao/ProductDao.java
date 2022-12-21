package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class ProductDao extends AbstractDao{
    private static String delete_id = "delete from ProductPojo productPojo where id=:id";
    private static String select_id = "select productPojo from ProductPojo productPojo where id=:id";
    private static String select_brand = "select productPojo from ProductPojo productPojo where brand=:brand";
    private static String select_all = "select productPojo from ProductPojo productPojo";


    @Transactional
    public void insert(ProductPojo productPojo) {
        entityManager().persist(productPojo);
    }

    public int delete(int id) {
        Query query = entityManager().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo productPojo) {
    }


}
