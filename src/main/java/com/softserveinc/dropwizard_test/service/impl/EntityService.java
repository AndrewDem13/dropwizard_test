package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.impl.MongoEntityDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.CrudService;

import javax.inject.Inject;
import java.util.List;

public class EntityService implements CrudService<Entity> {

    private final MongoEntityDao dao;

    @Inject
    public EntityService(MongoEntityDao entityDao) {
        this.dao = entityDao;
    }

    @Override
    public void create(Entity entity) {
         dao.create(entity);
    }

    @Override
    public Entity get(String message) {
        return (Entity) dao.get(message);
    }

    @Override
    public List<Entity> getAll() {
        return dao.getAll();
    }

    @Override
    public Entity update(String message, Entity entity) {
        return (Entity) dao.update(message, entity);
    }

    @Override
    public boolean delete(String message) {
        return dao.delete(message);
    }
}
