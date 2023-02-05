package com.increff.employee.dao;

import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderDaoTest extends AbstractUnitTest {
    @Autowired
    OrderDao orderDao;
    @Test
    public void testInsert(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(new Date());
        int orderId = orderDao.insert(orderPojo);
        assertTrue(orderId>0);
    }

    @Test
    public void testFilterId(){
        //add ids
        Date today = new Date();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(today);
        orderDao.insert(orderPojo);
        //create report date form object
        ReportDateForm reportDateForm = new ReportDateForm();
        Date startDate = new Date();
        startDate.setDate(today.getDate()-1);
        Date endDate = new Date();
        endDate.setDate(today.getDate()+1);
        reportDateForm.setStartDate(startDate.getTime());
        reportDateForm.setEndDate(endDate.getTime());
        //get filtered ids
        List<OrderPojo> orderPojoList = orderDao.filterId(reportDateForm);
        assertEquals(1,orderPojoList.size());
    }
    @Test
    public void testGetAllIds(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(new Date());
        orderDao.insert(orderPojo);

        List<OrderPojo> orderPojoList =  orderDao.getAllIds();
        assertEquals(1,orderPojoList.size());
    }
    @Test
    public void testGetDate(){
        //add OrderPojo
        Date today = new Date();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(today);
        orderDao.insert(orderPojo);
        //get OrderPojo ID
        int id = orderDao.getAllIds().get(0).getId();
        Date orderDate = orderDao.getDate(id);
        assertEquals(orderDate,today);
    }
}
