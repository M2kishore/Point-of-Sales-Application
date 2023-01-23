package com.increff.employee.spring;

import com.increff.employee.util.ScheduleUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    SchedulerConfig(){
        try{
            JobDetail job = JobBuilder.newJob(ScheduleUtil.class)
                    .withIdentity("job", "group1").build();
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job, trigger1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
