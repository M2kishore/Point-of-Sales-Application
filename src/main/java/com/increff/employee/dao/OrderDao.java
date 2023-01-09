package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDao extends AbstractDao{
    @Transactional
    public int insert(OrderPojo orderPojo){
        entityManager().persist(orderPojo);
        entityManager().flush();
        return orderPojo.getId();
    }

}
