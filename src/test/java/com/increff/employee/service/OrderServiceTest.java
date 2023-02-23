package com.increff.employee.service;

import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import org.apache.xpath.operations.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.Order;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest extends AbstractUnitTest {
    @Autowired
    private OrderService orderService;
    @Test
    public void testAddOrder() throws ApiException{
        //add pojo
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(new Date());
        int orderId = orderService.add(orderPojo);
        assertTrue(orderId>0);
    }

    @Test
    public void testCheckValidity() throws ApiException{
        try {
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(1);
            orderItemPojo.setProductId(1);
            orderItemPojo.setQuantity(-1);
            orderItemPojo.setSellingPrice(100);
            orderService.add(orderItemPojo);
        }catch (ApiException e){
            assertEquals("Quantity cannot be negative or Zero(0)",e.getMessage());
        }
        try {
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(1);
            orderItemPojo.setProductId(1);
            orderItemPojo.setQuantity(1);
            orderItemPojo.setSellingPrice(-100);
            orderService.add(orderItemPojo);
        }catch (ApiException e){
            assertEquals("Selling Price cannot be negative or Zero(0)",e.getMessage());
        }
        try {
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(1);
            orderItemPojo.setProductId(1);
            orderItemPojo.setQuantity(1);
            orderItemPojo.setSellingPrice(-100);
            orderService.add(orderItemPojo);
        }catch (ApiException e){
            assertEquals("Selling Price cannot be negative or Zero(0)",e.getMessage());
        }
        try {
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(1);
            orderItemPojo.setProductId(1);
            orderItemPojo.setQuantity(1);
            orderItemPojo.setSellingPrice(100.123);
            orderService.add(orderItemPojo);
        }catch (ApiException e){
            assertEquals("Selling Price must have less than 2 decimal places",e.getMessage());
        }
    }

    @Test
    public void testAddItem() throws ApiException{
        //get order id
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(new Date());
        int orderId = orderService.add(orderPojo);
        //add pojo
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(1);
        orderItemPojo.setSellingPrice(100);
        orderItemPojo.setQuantity(5);
        orderService.add(orderItemPojo);
    }

    @Test
    public void testGetAllOrders() throws ApiException{
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderService.add(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderService.add(orderItemPojo2);
        //get order list
        List<OrderItemPojo> orderItemPojoList = orderService.getAll();

        assertEquals(2,orderItemPojoList.size());
        assertFalse(orderItemPojoList.isEmpty());
    }
    @Test
    public void testGetOrders() throws ApiException{
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderService.add(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderService.add(orderItemPojo2);
        //get order
        List<OrderItemPojo> orderItemPojoList = orderService.get(1);
        assertFalse(orderItemPojoList.isEmpty());
        assertEquals(2,orderItemPojoList.size());
        try{
            orderItemPojoList = orderService.get(2);
        }catch (Exception e){
            assertEquals("Order with given ID does not exit, id: 2",e.getMessage());
        }

    }

    @Test
    public void testGetSingleNotPresent() throws ApiException{
        try {
            orderService.get(1);
        }catch (ApiException e){
            assertEquals("Order with given ID does not exit, id: 1",e.getMessage());
        }

    }

    @Test
    public void testGetAllIds() throws ApiException{
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(new Date());
        orderService.add(orderPojo);

        List<OrderPojo> orderPojoList =  orderService.getAllIds();
        assertEquals(1,orderPojoList.size());
    }

    @Test
    public void testGetOrdersUsingOrderId() throws ApiException{
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderService.add(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderService.add(orderItemPojo2);
        OrderItemPojo orderItemPojo3 = new OrderItemPojo();
        orderItemPojo3.setOrderId(2);
        orderItemPojo3.setProductId(1);
        orderItemPojo3.setSellingPrice(1000);
        orderItemPojo3.setQuantity(5);
        orderService.add(orderItemPojo3);
        OrderItemPojo orderItemPojo4 = new OrderItemPojo();
        orderItemPojo4.setOrderId(3);
        orderItemPojo4.setProductId(3);
        orderItemPojo4.setSellingPrice(500);
        orderItemPojo4.setQuantity(5);
        orderService.add(orderItemPojo4);

        List<OrderItemPojo> orderItemPojoList = orderService.get(1);
        assertEquals(2,orderItemPojoList.size());
    }
//    @Test
//    public void testInvalidOrderId
    @Test
    public void testFilterOrders() throws ApiException{
        //add orders
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(1);
        orderItemPojo1.setProductId(1);
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderService.add(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(1);
        orderItemPojo2.setProductId(2);
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(5);
        orderService.add(orderItemPojo2);
        OrderItemPojo orderItemPojo3 = new OrderItemPojo();
        orderItemPojo3.setOrderId(2);
        orderItemPojo3.setProductId(1);
        orderItemPojo3.setSellingPrice(1000);
        orderItemPojo3.setQuantity(5);
        orderService.add(orderItemPojo3);
        OrderItemPojo orderItemPojo4 = new OrderItemPojo();
        orderItemPojo4.setOrderId(3);
        orderItemPojo4.setProductId(3);
        orderItemPojo4.setSellingPrice(500);
        orderItemPojo4.setQuantity(5);
        orderService.add(orderItemPojo4);
        //get orders 1-2
        ReportOrderForm reportOrderForm = new ReportOrderForm();
        reportOrderForm.setStartId(1);
        reportOrderForm.setEndId(2);
        List<OrderItemPojo> orderItemPojoList = orderService.filterOrders(reportOrderForm);
        assertEquals(3,orderItemPojoList.size());
        //get orders 1-3
        reportOrderForm.setStartId(1);
        reportOrderForm.setEndId(3);
        orderItemPojoList = orderService.filterOrders(reportOrderForm);
        assertEquals(4,orderItemPojoList.size());
    }
    
    @Test
    public void testfilterId() throws ApiException{
        //add ids
        Date today = new Date();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(today);
        orderService.add(orderPojo);
        //create report date form object
        ReportDateForm reportDateForm = new ReportDateForm();
        Date startDate = new Date();
        startDate.setDate(today.getDate()-1);
        Date endDate = new Date();
        endDate.setDate(today.getDate()+1);
        reportDateForm.setStartDate(startDate.getTime());
        reportDateForm.setEndDate(endDate.getTime());
        //get filtered ids
        List<OrderPojo> orderPojoList = orderService.filterId(reportDateForm);
        assertEquals(1,orderPojoList.size());
    }
    @Test
    public void testGetDate(){
        //add OrderPojo
        Date today = new Date();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate(today);
        orderService.add(orderPojo);
        //get OrderPojo ID
        int id = orderService.getAllIds().get(0).getId();
        Date orderDate = orderService.getDate(id);
        assertEquals(orderDate,today);
    }
}
