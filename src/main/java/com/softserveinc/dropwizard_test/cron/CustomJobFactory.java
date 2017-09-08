package com.softserveinc.dropwizard_test.cron;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.inject.Inject;

public class CustomJobFactory implements JobFactory {

    @Inject
    StatisticsReportJob job;

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        if (bundle.getJobDetail().getJobClass() == job.getClass()) {
            return job;
        } else {
            return new SimpleJobFactory().newJob(bundle, scheduler);
        }
    }
}
