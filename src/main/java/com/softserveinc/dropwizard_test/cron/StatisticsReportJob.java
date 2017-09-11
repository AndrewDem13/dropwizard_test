package com.softserveinc.dropwizard_test.cron;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class StatisticsReportJob implements Job {

    @Inject
    private EntityService service;

    @Inject
    private MetricRegistry customMetricRegistry;

    private Timer cronJobTimer;

    @PostConstruct
    public void init() {
        cronJobTimer = customMetricRegistry.timer("Cron Job Timer");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Timer.Context jobExecution = cronJobTimer.time();
        System.out.println(" ===========  Here's Job execution begins ================");
        System.out.println("  Total records in DB by now: " + service.getAll().size());
        jobExecution.stop();
        System.out.println(String.format("  This operation took %6.5f seconds in mean", cronJobTimer.getMeanRate()));
        System.out.println(" ===========  And now it's done! ================");
    }
}
