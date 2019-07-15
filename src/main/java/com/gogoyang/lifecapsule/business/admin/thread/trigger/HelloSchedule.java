package com.gogoyang.lifecapsule.business.admin.thread.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloSchedule {
    public static void startHello() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        System.out.println("主程序开始时间:" + sf.format(date));

        //start time
        date.setTime(date.getTime() + 3000);
        //end time
        Date endDate=new Date();
        endDate.setTime(endDate.getTime()+6000);

        //创建JobDetail实例，与HelloJob class绑定
        JobDetail jobDetail = JobBuilder
                .newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .build();

        //创建一个trigger实例
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .startAt(date)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2)
                                .repeatForever()
                )
                .build();

        //创建schedule实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        try {
            Scheduler scheduler = sfact.getScheduler();
            scheduler.start();
            Date schedulerDate = new Date();
            System.out.println("schedule开始时间: " + sf.format(schedulerDate));

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
