package com.softserveinc.dropwizard_test.db.impl;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;

import javax.inject.Inject;
import java.util.List;

public class MongoEntityDaoAdapter implements CrudDao<Entity> {

    private MongoEntityDao mongoEntityDao;

    @Inject
    public MongoEntityDaoAdapter(MongoEntityDao mongoEntityDao) {
        this.mongoEntityDao = mongoEntityDao;
    }

    @Override
    public void create(Entity entity) {
        mongoEntityDao.create(entity);
    }

    @Override
    public Entity get(int id) {
        return mongoEntityDao.get(id);
    }

    @Override
    public List<Entity> getAll() {
        return mongoEntityDao.getAll();
    }

    @Override
    public Entity update(Entity entity) {
        return mongoEntityDao.update(entity);
    }

    @Override
    public boolean delete(int id) {
        return mongoEntityDao.delete(id);
    }
}
