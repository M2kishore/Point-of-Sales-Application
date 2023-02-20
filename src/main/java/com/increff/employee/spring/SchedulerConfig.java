package com.increff.employee.spring;

import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.model.form.ReportOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    OrderService orderService;
    @Autowired
    SchedulerService schedulerService;
    Date startDate = new Date();
    Date endDate = new Date();
//    @Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void Schedule(){
        System.out.println("Scheduler Started");
        endDate.setDate(startDate.getDate() + 1);
        java.util.Calendar calendarStart = java.util.Calendar.getInstance();
        java.util.Calendar calendarEnd = java.util.Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarEnd.setTime(endDate);
        java.util.Calendar calendarStartConverted = makeTimeZero(calendarStart);
        Calendar calendarEndConverted = makeTimeZero(calendarEnd);
        long startDateMilliseconds = calendarStartConverted.getTimeInMillis();
        long endDateMilliseconds = calendarEndConverted.getTimeInMillis();
        ReportDateForm reportDateForm = new ReportDateForm();
        reportDateForm.setStartDate(startDateMilliseconds);
        reportDateForm.setEndDate(endDateMilliseconds);
        List<OrderPojo> orderPojoList = orderService.filterId(reportDateForm);
        if(orderPojoList.size() == 0){
            System.out.println("No orders for today");
            return;
        }
        OrderPojo endObject = orderPojoList.get(0);
        OrderPojo startObject = orderPojoList.get(orderPojoList.size()-1);
        ReportOrderForm reportOrderForm = new ReportOrderForm();
        reportOrderForm.setStartId(startObject.getId());
        reportOrderForm.setEndId(endObject.getId());

        List<OrderItemPojo> orderItemPojoList =  orderService.filterOrders(reportOrderForm);

        double revenue = 0;
        int quantity = 0;
        int count = orderPojoList.size();
        for(OrderItemPojo order: orderItemPojoList){
            quantity += order.getQuantity();
            revenue += order.getSellingPrice();
        }
        System.out.println("count: "+count+" quantity: "+quantity+" revenue: "+revenue);

        SchedulerPojo schedulerPojo = new SchedulerPojo(startDate,count,quantity,revenue);
        schedulerService.add(schedulerPojo);
        System.out.println("Schedule Complete");
    }
    private Calendar makeTimeZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
