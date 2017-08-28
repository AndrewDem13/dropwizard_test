package com.softserveinc.dropwizard_test;

import com.mongodb.MongoClient;
import com.softserveinc.dropwizard_test.healthChecks.MongoHealthCheck;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class App extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {

    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient(configuration.mongohost, configuration.mongoport);
        configuration.setMongoClient(mongoClient);

        environment.healthChecks().register("MongoDB", new MongoHealthCheck(mongoClient));

        environment.jersey().register(new DependencyBinder(configuration));
        environment.jersey().register(EntityResource.class);
    }
}
