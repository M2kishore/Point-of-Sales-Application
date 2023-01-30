package com.increff.employee.dao;

import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {
    private static String select_all = "select orderItemPojo from OrderItemPojo orderItemPojo";
    private static String select_id = "select orderItemPojo from OrderItemPojo orderItemPojo where orderId=:orderId";
    private static String select_orders = "SELECT orderItemPojo FROM OrderItemPojo AS orderItemPojo WHERE orderItemPojo.orderId BETWEEN :startOrderId AND :endOrderId";

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
    public List<OrderItemPojo> filterOrder(ReportOrderForm reportOrderForm){
        TypedQuery<OrderItemPojo> query = getQuery(select_orders,OrderItemPojo.class);
        query.setParameter("startOrderId",reportOrderForm.getStartId());
        query.setParameter("endOrderId",reportOrderForm.getEndId());
        return query.getResultList();
    }
}
