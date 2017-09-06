package com.softserveinc.dropwizard_test.cron;

import com.softserveinc.dropwizard_test.messaging.impl.RabbitConsumer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

public class StatisticsReportJob implements Job {

    @Inject
    private RabbitConsumer rabbitConsumer;

    public StatisticsReportJob() {
    }

//    @Inject
//    public void setRabbitConsumer(RabbitConsumer rabbitConsumer) {
//        this.rabbitConsumer = rabbitConsumer;
//    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(" ===========  Here's Job execution begins ================");
        System.out.println("Updates since last report: " + rabbitConsumer.getCurrentUpdatesCount());
        System.out.println("Creates since last report: " + rabbitConsumer.getCurrentCreatesCount());
        rabbitConsumer.setCurrentCreatesCount(0);
        rabbitConsumer.setCurrentUpdatesCount(0);
        System.out.println("Updates total count: " + rabbitConsumer.getAllUpdatesCount());
        System.out.println("Creates total count: " + rabbitConsumer.getAllCreatesCount());
        System.out.println(" ===========  And now it's done! ================");
    }
}
