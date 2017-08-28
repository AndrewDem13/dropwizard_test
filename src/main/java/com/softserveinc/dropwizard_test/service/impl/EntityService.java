package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.CrudService;

import javax.inject.Inject;
import java.util.List;

public class EntityService implements CrudService<Entity> {

    private final CrudDao<Entity> dao;

    @Inject
    public EntityService(CrudDao<Entity> dao) {
        this.dao = dao;
    }

    @Override
    public void create(Entity entity) {
        if (dao.get(entity.getId()) == null) {
            dao.create(entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public Entity get(int id) {
        return dao.get(id);
    }

    @Override
    public List<Entity> getAll() {
        return dao.getAll();
    }

    @Override
    public Entity update(Entity entity) {
        if (dao.get(entity.getId()) != null) {
            return dao.update(entity);
        } else {
            dao.create(entity);
            return entity;
        }
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
