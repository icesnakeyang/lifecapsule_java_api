package com.gogoyang.lifecapsule.business.admin.backJob;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {
    private static final int TIME = 10;

    @Bean
    public JobDetail gogoKeyProcessorJobDetail() {
        return JobBuilder.newJob(GogoKeyProcessor.class)
                .withIdentity("gogoKeyProcessorJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger gogoKeyProcessorTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(TIME)
                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(gogoKeyProcessorJobDetail())
                .withIdentity("gogoKeyProcessorJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
