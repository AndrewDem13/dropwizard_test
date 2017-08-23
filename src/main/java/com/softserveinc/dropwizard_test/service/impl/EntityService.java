package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.db.EntityDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.BasicService;

import javax.inject.Inject;
import java.util.List;

public class EntityService implements BasicService<Entity> {

    private final EntityDao entityDao;

    @Inject
    public EntityService(EntityDao entityDao) {
        this.entityDao = entityDao;
    }

    @Override
    public void createEntity(Entity entity) {
         entityDao.createEntity(entity);
    }

    @Override
    public Entity getEntity(String message) {
        return entityDao.getEntity(message);
    }

    @Override
    public List<Entity> getAll() {
        return entityDao.getAll();
    }

    @Override
    public Entity updateEntity(String message, Entity entity) {
        return entityDao.updateEntity(message, entity);
    }

    @Override
    public boolean deleteEntity(String message) {
        return entityDao.deleteEntity(message);
    }
}
