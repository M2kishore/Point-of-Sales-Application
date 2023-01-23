package com.increff.employee.util;
import com.increff.employee.controller.api.OrderApiController;
import com.increff.employee.controller.api.ReportApiController;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.OrderService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleUtil implements Job {

    private static Logger logger = Logger.getLogger(ScheduleUtil.class);
    @Autowired
    ReportApiController reportApiController;
    Date startDate = new Date();
    Date endDate = new Date();
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Scheduler Started");
        startDate.setDate(startDate.getDate() - 10);
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarEnd.setTime(endDate);
        Calendar calendarStartConverted = makeTimeZero(calendarStart);
        Calendar calendarEndConverted = makeTimeZero(calendarEnd);
        long startDateMilliseconds = calendarStartConverted.getTimeInMillis();
        long endDateMilliseconds = calendarEndConverted.getTimeInMillis();
        ReportDateForm reportDateForm = new ReportDateForm();
        reportDateForm.setStartDate(startDateMilliseconds);
        reportDateForm.setEndDate(endDateMilliseconds);
        System.out.println(reportDateForm.getStartDate()+" "+reportDateForm.getEndDate());
        List<Integer> orderPojoList = reportApiController.getAll(reportDateForm);
        System.out.println(orderPojoList.size());
        System.out.println("Schedule Complete");
    }
    private Calendar makeTimeZero(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    private List<Integer> convertPojoToData(List<OrderPojo> orderPojoList){
        List<Integer> list2 = new ArrayList<Integer>();
        for( OrderPojo orderPojo: orderPojoList){
            list2.add(orderPojo.getId());
        }
        return list2;
    }
}