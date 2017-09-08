package com.softserveinc.dropwizard_test.cron;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class CronJobConfigurationFeature implements Feature {

    @Inject
    private CustomJobFactory statisticsReportJobFactory;

    @Override
    public boolean configure(FeatureContext context) {
        // create job
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()) // every 10 seconds
                    .build();

            JobDetail job = newJob(StatisticsReportJob.class)
                .withIdentity("job1", "group1")
                .build();

            scheduler.setJobFactory(statisticsReportJobFactory);
            scheduler.scheduleJob(job, trigger);

//            JobDetail testJob = newJob(TestJob.class)
//                    .withIdentity("testJob", "group1")
//                    .build();
//            Trigger testTrigger = newTrigger()
//                    .withIdentity("trigger2", "group1")
//                    .startAt(Date.from(Instant.now().plus(2, ChronoUnit.SECONDS)))
//                    .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
//                    .build();
//            scheduler.scheduleJob(testJob, testTrigger);

            scheduler.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
