package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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
    public void add(OrderItemPojo newOrderItemPojo) throws ApiException{
        checkValidity(newOrderItemPojo);
        orderItemDao.insert(newOrderItemPojo);
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<OrderItemPojo> get(int orderId) throws ApiException {
        return getCheck(orderId);
    }
    @Transactional
    public List<OrderItemPojo> getCheck(int id) throws ApiException {
        List<OrderItemPojo> orderItemPojo = orderItemDao.select(id);
        if (orderItemPojo.isEmpty()) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return orderItemPojo;
    }
    @Transactional
    public List<OrderItemPojo> getAll(){
        return orderItemDao.selectAll();
    }

    @Transactional
    public List<OrderPojo> getAllIds(){
        return orderDao.getAllIds();
    }
    @Transactional
    public List<OrderPojo> filterId(ReportDateForm reportDateForm){
        return orderDao.filterId(reportDateForm);
    }
    @Transactional
    public List<OrderItemPojo> filterOrders(ReportOrderForm reportOrderForm){
        return orderItemDao.filterOrder(reportOrderForm);
    }

    public Date getDate(int orderId) {
        return orderDao.getDate(orderId);
    }

    private void checkValidity(OrderItemPojo newOrderItemPojo) throws ApiException {
        if(NumberUtil.isNegative((double)newOrderItemPojo.getQuantity())){
            throw new ApiException("Quantity cannot be negative or Zero(0)");
        }
        if(NumberUtil.isNegative(newOrderItemPojo.getSellingPrice())){
            throw new ApiException("Selling Price cannot be negative or Zero(0)");
        }
        if(!NumberUtil.has2DecimalPlaces(newOrderItemPojo.getSellingPrice())){
            throw new ApiException("Selling Price must have less than 2 decimal places");
        }
    }
}