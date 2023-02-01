package com.increff.employee.service;

import com.increff.employee.dao.SchedulerDao;
import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SchedulerService {
    @Autowired
    SchedulerDao schedulerDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(SchedulerPojo schedulerPojo){
        schedulerDao.insert(schedulerPojo);
    }
    @Transactional
    public List<SchedulerPojo> getAll(){
        return schedulerDao.selectAll();
    }
}
