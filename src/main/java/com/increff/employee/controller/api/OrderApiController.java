package com.increff.employee.controller.api;

import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.DateUtil;
import com.increff.employee.model.data.OrderItemData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value="Gets a product by Barcode")
    @RequestMapping(path="/api/order",method = RequestMethod.GET)
    public OrderItemData get(@RequestParam String barcode) throws ApiException {
        ProductPojo productPojo = productService.getBarcode(barcode);
        return convertPojoToData(productPojo);
    }
    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/order/all", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        List<OrderItemPojo> list = orderService.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderItemPojo orderItemPojo : list) {
            list2.add(convertPojoToData(orderItemPojo));
        }
        return list2;
    }
    @ApiOperation(value = "add date and time for orders")
    @RequestMapping(path="/api/order",method = RequestMethod.POST)
    public int add(@RequestBody OrderForm form)throws ApiException{
        OrderPojo newOrderPojo = convertFormToPojo(form);
        return orderService.add(newOrderPojo);
    }

    @ApiOperation(value = "Place an order")
    @RequestMapping(path = "/api/order/order",method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form)throws ApiException{
        OrderItemPojo newOrderItemPojo = convertFormToPojo(form);
        orderService.add(newOrderItemPojo);
    }
    @ApiOperation(value="Deletes a Cancelled Order")
    @RequestMapping(path="/api/order/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        orderService.delete(id);
    }

    private OrderItemPojo convertFormToPojo(OrderItemForm orderItemForm){
        OrderItemPojo newOrderItemPojo = new OrderItemPojo();
        newOrderItemPojo.setOrderId(orderItemForm.getOrderId());
        newOrderItemPojo.setProductId(orderItemForm.getProductId());
        newOrderItemPojo.setQuantity(orderItemForm.getQuantity());
        newOrderItemPojo.setSellingPrice(orderItemForm.getSellingPrice());
        return newOrderItemPojo;
    }
    private OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo newOrderPojo = new OrderPojo();
        newOrderPojo.setDate(DateUtil.MillisecondToDate(orderForm.getDate()));
        return newOrderPojo;
    }
    private OrderItemData convertPojoToData(ProductPojo productPojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setName(productPojo.getName());
        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setSellingPrice(productPojo.getMrp());
        orderItemData.setProductId(productPojo.getId());
        return orderItemData;
    }

    private OrderData convertPojoToData(OrderItemPojo orderItemPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderItemPojo.getId());
        orderData.setOrderId(orderItemPojo.getOrderId());
        orderData.setProductId(orderItemPojo.getProductId());
        orderData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderData;
    }

}
