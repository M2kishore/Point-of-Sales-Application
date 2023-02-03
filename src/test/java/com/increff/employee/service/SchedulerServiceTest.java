package com.increff.employee.service;

import com.increff.employee.pojo.SchedulerPojo;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SchedulerServiceTest extends AbstractUnitTest{
    @Autowired
    private SchedulerService schedulerService;
    @Test
    public void testAdd() throws ApiException{
        SchedulerPojo schedulerPojo = new SchedulerPojo(new Date(),3,12,334);
        schedulerService.add(schedulerPojo);
        List<SchedulerPojo> schedulerPojoList = schedulerService.getAll();
        assertEquals(1,schedulerPojoList.size());
    }
    @Test
    public void testGetAll(){
        SchedulerPojo schedulerPojo1 = new SchedulerPojo(new Date(),3,12,300);
        SchedulerPojo schedulerPojo2 = new SchedulerPojo(new Date(),2,10,304);
        SchedulerPojo schedulerPojo3 = new SchedulerPojo(new Date(),4,1,3);
        schedulerService.add(schedulerPojo1);
        schedulerService.add(schedulerPojo2);
        schedulerService.add(schedulerPojo3);
        List<SchedulerPojo> schedulerPojoList = schedulerService.getAll();
        assertEquals(3,schedulerPojoList.size());
    }
}
