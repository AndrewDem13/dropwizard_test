package com.softserveinc.dropwizard_test.db.util;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.mongo.MongoEntityDaoAdapter;
import com.softserveinc.dropwizard_test.db.myBatis.MyBatisEntityDaoAdapter;
import com.softserveinc.dropwizard_test.entity.Entity;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;

public class DaoFactory implements Factory<CrudDao<Entity>> {

    private final MongoEntityDaoAdapter mongoAdapter;
    private final MyBatisEntityDaoAdapter myBatisAdapter;

    private final DbSwitcher dbSwitcher;

    @Inject
    public DaoFactory(DbSwitcher dbSwitcher, MongoEntityDaoAdapter mongoAdapter, MyBatisEntityDaoAdapter myBatisAdapter) {
        this.mongoAdapter = mongoAdapter;
        this.myBatisAdapter = myBatisAdapter;
        this.dbSwitcher = dbSwitcher;
    }

    @Override
    public CrudDao<Entity> provide() {
        if (dbSwitcher.getSqlDb()) {
            return myBatisAdapter;
        } else {
            return mongoAdapter;
        }
    }

    @Override
    public void dispose(CrudDao<Entity> instance) {
        // not used
    }
}
