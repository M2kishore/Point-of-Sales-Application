package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public class ProductDao extends AbstractDao{
    private static String select_id = "select productPojo from ProductPojo productPojo where id=:id";
    private static String select_barcode = "select productPojo from ProductPojo productPojo where barcode=:barcode";
    private static String select_brand = "select productPojo from ProductPojo productPojo where brand=:brand";
    private static String select_all = "select productPojo from ProductPojo productPojo";


    @Transactional
    public int insert(ProductPojo productPojo) {
        entityManager().persist(productPojo);
        entityManager().flush();
        return productPojo.getId();
    }
    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public ProductPojo selectBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(select_barcode, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo productPojo) {
    }


}
