package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Repository
public class OrderDao extends AbstractDao{
    private static String delete_id = "delete from OrderPojo orderPojo where id=:id";
    @Transactional
    public int insert(OrderPojo orderPojo){
        entityManager().persist(orderPojo);
        entityManager().flush();
        return orderPojo.getId();
    }

    public int delete(int id){
        Query query = entityManager().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

}
