package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {
    private static String select_all = "select orderItemPojo from OrderItemPojo orderItemPojo";
    private static String select_id = "select orderItemPojo from OrderItemPojo orderItemPojo where orderId=:orderId";

    @Transactional
    public void insert(OrderItemPojo orderItemPojo){
        entityManager().persist(orderItemPojo);
    }

    public List<OrderItemPojo> select(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
        return query.getResultList();
    }
    public void update(OrderItemPojo orderItemPojo){
    }

}
