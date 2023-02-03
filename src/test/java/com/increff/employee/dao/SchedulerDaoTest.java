package com.increff.employee.dao;

import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SchedulerDaoTest extends AbstractUnitTest {
    @Autowired
    SchedulerDao schedulerDao;
    @Test
    public void testInsert(){
        SchedulerPojo schedulerPojo = new SchedulerPojo(new Date(),3,12,334);
        schedulerDao.insert(schedulerPojo);
        List<SchedulerPojo> schedulerPojoList = schedulerDao.selectAll();
        assertEquals(1,schedulerPojoList.size());
    }
    @Test
    public void testSelectAll(){
        SchedulerPojo schedulerPojo1 = new SchedulerPojo(new Date(),3,12,300);
        SchedulerPojo schedulerPojo2 = new SchedulerPojo(new Date(),2,10,304);
        SchedulerPojo schedulerPojo3 = new SchedulerPojo(new Date(),4,1,3);
        schedulerDao.insert(schedulerPojo1);
        schedulerDao.insert(schedulerPojo2);
        schedulerDao.insert(schedulerPojo3);
        List<SchedulerPojo> schedulerPojoList = schedulerDao.selectAll();
        assertEquals(3,schedulerPojoList.size());
    }
}
