package com.increff.employee.dao;



import com.increff.employee.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{
    private static String delete_id = "delete from InventoryPojo inventoryPojo where id=:id";
    private static String select_id = "select inventoryPojo from InventoryPojo inventoryPojo where id=:id";
    private static String select_all = "select inventoryPojo from InventoryPojo inventoryPojo";

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        entityManager().persist(inventoryPojo);
    }

    public int delete(int id) {
        Query query = entityManager().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public InventoryPojo select(int id) {
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo inventoryPojo) {
    }
}
