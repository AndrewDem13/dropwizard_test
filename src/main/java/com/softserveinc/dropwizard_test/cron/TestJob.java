package com.softserveinc.dropwizard_test.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

    public TestJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(" ===========  Here's TestJob runs ================");
        System.out.println("||");
        System.out.println("||");
        System.out.println("||");
        System.out.println("||");
        System.out.println(" ============ Finish ================");
    }
}
