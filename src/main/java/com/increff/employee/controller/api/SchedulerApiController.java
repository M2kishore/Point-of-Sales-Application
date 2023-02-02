package com.increff.employee.controller.api;

import com.increff.employee.model.data.SchedulerData;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class SchedulerApiController {
    @Autowired
    private SchedulerService schedulerService;

    @ApiOperation(value = "Gets list of all scheduled list")
    @RequestMapping(path = "/api/scheduler", method = RequestMethod.GET)
    public List<SchedulerData> getAll() {
        List<SchedulerPojo> list = schedulerService.getAll();
        List<SchedulerData> list2 = new ArrayList<SchedulerData>();
        for (SchedulerPojo schedulerPojo : list) {
            SchedulerData schedulerData = new SchedulerData(schedulerPojo.getDate(),schedulerPojo.getCount(),schedulerPojo.getQuantity(),schedulerPojo.getRevenue());
            list2.add(schedulerData);
        }
        return list2;
    }
}
