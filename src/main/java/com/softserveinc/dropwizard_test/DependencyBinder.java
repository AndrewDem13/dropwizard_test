package com.softserveinc.dropwizard_test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.softserveinc.dropwizard_test.db.EntityDao;
import com.softserveinc.dropwizard_test.db.impl.MongoEntityDao;
import com.softserveinc.dropwizard_test.service.BasicService;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyBinder extends AbstractBinder {

    private final AppConfiguration configuration;

    public DependencyBinder(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient(configuration.mongohost, configuration.mongoport);
        MongoDatabase database = mongoClient.getDatabase(configuration.mongodb).withCodecRegistry(pojoCodecRegistry);

        bind(database).to(MongoDatabase.class);
        bind(MongoEntityDao.class).to(EntityDao.class);
        bind(EntityService.class).to(BasicService.class);
    }
}