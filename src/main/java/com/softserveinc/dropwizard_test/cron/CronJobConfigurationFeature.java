package com.softserveinc.dropwizard_test.cron;

import com.softserveinc.dropwizard_test.messaging.impl.RabbitConsumer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class CronJobConfigurationFeature implements Feature {

    @Inject
    private StatisticsReportJob job;

    @Override
    public boolean configure(FeatureContext context) {
        // create job
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()) // every 10 seconds
                    .build();

            JobDetail job = newJob(StatisticsReportJob.class)
                .withIdentity("job1", "group1")
                .build();

            sched.scheduleJob(job, trigger);

            sched.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        context.register(new AbstractBinder() {
//            @Override
//            protected void configure() {
//                bind()
//            }
//        });
        return true;
    }
}
