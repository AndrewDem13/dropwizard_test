package com.softserveinc.dropwizard_test.healthChecks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.client.MongoDatabase;

@Deprecated
public class MongoHealthCheck extends HealthCheck {
    private MongoDatabase mongo;

    public MongoHealthCheck(MongoDatabase mongo) {
        super();
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {
        mongo.listCollections();
        return Result.healthy();
    }
}
