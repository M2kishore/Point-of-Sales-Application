package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Transactional(rollbackOn = ApiException.class)
    public int add (OrderPojo newOrderPojo){
        return orderDao.insert(newOrderPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo newOrderItemPojo){
        orderItemDao.insert(newOrderItemPojo);
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<OrderItemPojo> get(int orderId) throws ApiException {
        return getCheck(orderId);
    }
    @Transactional
    public List<OrderItemPojo> getCheck(int id) throws ApiException {
        List<OrderItemPojo> orderItemPojo = orderItemDao.select(id);
        if (orderItemPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return orderItemPojo;
    }
    @Transactional
    public void delete(int id){
        orderDao.delete(id);
    }
}