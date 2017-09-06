package com.softserveinc.dropwizard_test;

import com.mongodb.MongoClient;
import com.softserveinc.dropwizard_test.cron.StatisticsReportJob;
import com.softserveinc.dropwizard_test.healthChecks.MongoHealthCheck;
import com.softserveinc.dropwizard_test.cron.CronJobConfigurationFeature;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class App extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {

    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        MongoClient mongoClient = new MongoClient(configuration.mongohost, configuration.mongoport);
        configuration.setMongoClient(mongoClient);

        environment.healthChecks().register("MongoDB", new MongoHealthCheck(mongoClient));

        environment.jersey().register(new DependencyBinder(configuration));
        environment.jersey().register(EntityResource.class);
        environment.jersey().register(CronJobConfigurationFeature.class);







        // Metrics' reporting to the console
//        ConsoleReporter reporter = ConsoleReporter
//                .forRegistry(environment.metrics())
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .build();
//        reporter.start(10, TimeUnit.SECONDS);
    }
}
