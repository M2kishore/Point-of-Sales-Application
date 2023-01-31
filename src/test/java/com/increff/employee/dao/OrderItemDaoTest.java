package com.increff.employee.dao;

import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderItemDaoTest extends AbstractUnitTest {
    @Autowired
    OrderItemDao orderItemDao;

    @Test
    public void testInsert(){
        //add pojo
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(1);
        orderItemPojo.setProductId(1);
        orderItemPojo.setSellingPrice(100);
        orderItemPojo.setQuantity(5);
        orderItemDao.insert(orderItemPojo);
    }
    @Test
    public void testSelect(){
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderItemDao.insert(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderItemDao.insert(orderItemPojo2);
        //get order
        List<OrderItemPojo> orderItemPojoList = orderItemDao.select(1);
        assertFalse(orderItemPojoList.isEmpty());
        assertEquals(2,orderItemPojoList.size());
    }
    @Test
    public void testSelectAll(){
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderItemDao.insert(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderItemDao.insert(orderItemPojo2);
        //get order list
        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll();

        assertEquals(2,orderItemPojoList.size());
        assertFalse(orderItemPojoList.isEmpty());
    }

    @Test
    public void testfilterOrder(){
        //add orders
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderItemDao.insert(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderItemDao.insert(orderItemPojo2);
        OrderItemPojo orderItemPojo3 = new OrderItemPojo();
        orderItemPojo3.setOrderId(2);
        orderItemPojo3.setProductId(1);
        orderItemPojo3.setSellingPrice(1000);
        orderItemPojo3.setQuantity(5);
        orderItemDao.insert(orderItemPojo3);
        OrderItemPojo orderItemPojo4 = new OrderItemPojo();
        orderItemPojo4.setOrderId(3);
        orderItemPojo4.setProductId(3);
        orderItemPojo4.setSellingPrice(500);
        orderItemPojo4.setQuantity(5);
        orderItemDao.insert(orderItemPojo4);
        //get orders 1-2
        ReportOrderForm reportOrderForm = new ReportOrderForm();
        reportOrderForm.setStartId(1);
        reportOrderForm.setEndId(2);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.filterOrder(reportOrderForm);
        assertEquals(3,orderItemPojoList.size());
        //get orders 1-3
        reportOrderForm.setStartId(1);
        reportOrderForm.setEndId(3);
        orderItemPojoList = orderItemDao.filterOrder(reportOrderForm);
        assertEquals(4,orderItemPojoList.size());
    }

}
