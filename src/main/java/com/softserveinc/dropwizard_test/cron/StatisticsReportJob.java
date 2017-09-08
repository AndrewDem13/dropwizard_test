package com.softserveinc.dropwizard_test.cron;

import com.softserveinc.dropwizard_test.service.CreateUpdateCounter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

public class StatisticsReportJob implements Job {

    @Inject
    CreateUpdateCounter counter;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(" ===========  Here's Job execution begins ================");
        System.out.println("Creates since last report: " + counter.getCurrentCreatesCount());
        System.out.println("Updates since last report: " + counter.getCurrentUpdatesCount());
        System.out.println("Creates total count: " + counter.getTotalCreatesCount());
        System.out.println("Updates total count: " + counter.getTotalUpdatesCount());
        System.out.println(" ===========  And now it's done! ================");
    }
}
