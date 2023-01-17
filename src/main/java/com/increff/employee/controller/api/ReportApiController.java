package com.increff.employee.controller.api;

import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ReportApiController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Gets list of all orders in the given time frame")
    @RequestMapping(path = "/api/report", method = RequestMethod.POST)
    public List<Integer> getAll(@RequestBody ReportDateForm reportDateForm) {
        List<OrderPojo> list = orderService.filterId(reportDateForm);
        List<Integer> list2 = new ArrayList<Integer>();
        for( OrderPojo orderPojo: list){
            list2.add(orderPojo.getId());
        }
        return list2;
    }
    @ApiOperation(value = "Get list of orders between the ids")
    @RequestMapping(path = "/api/report/order", method = RequestMethod.POST)
    public List<OrderData> getAll(@RequestBody ReportOrderForm reportOrderForm){
        List<OrderItemPojo> list = orderService.filterOrders(reportOrderForm);
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderItemPojo orderItemPojo : list) {
            list2.add(convertPojoToData(orderItemPojo));
        }
        return list2;
    }
    private OrderData convertPojoToData(OrderItemPojo orderItemPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderItemPojo.getId());
        orderData.setOrderId(orderItemPojo.getOrderId());
        orderData.setProductId(orderItemPojo.getProductId());
        orderData.setSellingPrice(orderItemPojo.getSellingPrice());
        orderData.setQuantity(orderItemPojo.getQuantity());
        return orderData;
    }
}
