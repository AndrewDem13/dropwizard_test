package com.softserveinc.dropwizard_test;

import com.softserveinc.dropwizard_test.resource.EntityResource;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class App extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        final EntityService entityService = new EntityService();
        environment.jersey().register(new EntityResource(entityService));
    }
}
