package com.softserveinc.dropwizard_test.healthChecks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public MongoHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        MongoDatabase database;
        try {
            database = mongoClient.getDatabase("healthCheck");
            database.createCollection("test");
        } catch (Exception e) {
            return Result.unhealthy("Can't connect to Mongo server");
        }
        database.drop();
        return Result.healthy();
    }
}
