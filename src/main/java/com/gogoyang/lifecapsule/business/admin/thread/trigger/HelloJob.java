package com.gogoyang.lifecapsule.business.admin.thread.trigger;

import lombok.Data;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HelloJob implements Job {
    private String message;
    private Float floatJobValue;
    private Double doubleTriggerValue;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();

        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        System.out.println("处理进程时间: " + sf.format(date));

        Trigger currentTrigger = context.getTrigger();
        System.out.println("start time is:" + sf.format(currentTrigger.getStartTime()));
        System.out.println("end time is :" + sf.format(currentTrigger.getEndTime()));

        JobKey jobKey = currentTrigger.getJobKey();
        System.out.println("job key info is :" + "jobname:" + jobKey.getName() + "jobgroup:" + jobKey.getGroup());
    }
}