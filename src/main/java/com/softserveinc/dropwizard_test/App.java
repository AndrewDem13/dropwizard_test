package com.softserveinc.dropwizard_test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.mongodb.MongoClient;
import com.softserveinc.dropwizard_test.cron.CronJobConfigurationFeature;
import com.softserveinc.dropwizard_test.healthChecks.MongoHealthCheck;
import com.softserveinc.dropwizard_test.messaging.impl.RabbitConsumer;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.concurrent.TimeUnit;


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

        MetricRegistry customMetricRegistry = new MetricRegistry();

        environment.jersey().register(new DependencyBinder(configuration, customMetricRegistry));
        environment.jersey().register(EntityResource.class);

        environment.jersey().register(CronJobConfigurationFeature.class);
        environment.jersey().register(RabbitConsumer.class);

        // Metrics' reporting to the console
        ConsoleReporter reporter = ConsoleReporter
                .forRegistry(customMetricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);
        reporter.report();
    }
}
