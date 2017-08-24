package com.softserveinc.dropwizard_test;

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
        environment.jersey().register(new DependencyBinder(configuration));
        environment.jersey().register(EntityResource.class);
    }
}
