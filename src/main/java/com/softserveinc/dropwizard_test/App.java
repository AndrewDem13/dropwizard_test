package com.softserveinc.dropwizard_test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.softserveinc.dropwizard_test.db.impl.MongoEntityDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.healthChecks.MongoHealthCheck;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import com.softserveinc.dropwizard_test.resource.IndexResource;
import com.softserveinc.dropwizard_test.service.BasicService;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


public class App extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(configuration.mongohost, configuration.mongoport);
        MongoDatabase database = mongoClient.getDatabase(configuration.mongodb).withCodecRegistry(pojoCodecRegistry);
        environment.healthChecks().register("MongoDb", new MongoHealthCheck(database));

        final BasicService entityService = new EntityService(new MongoEntityDao(database.getCollection("entities", Entity.class)));
        environment.jersey().register(new EntityResource(entityService));

        environment.jersey().register(new IndexResource());
    }
}
