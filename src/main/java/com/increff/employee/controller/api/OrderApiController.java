package com.increff.employee.controller.api;

import com.increff.employee.model.form.OrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class OrderApiController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Places an Order")
    @RequestMapping(path="/api/order",method = RequestMethod.POST)
    public int add(@RequestBody OrderForm form)throws ApiException{
        OrderPojo newOrderPojo = convertFormToPojo(form);
        return orderService.add(newOrderPojo);
    }

    private static OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo newOrderPojo = new OrderPojo();
        newOrderPojo.setDate(DateUtil.MillisecondToDate(orderForm.getDate()));
        return newOrderPojo;
    }
}
