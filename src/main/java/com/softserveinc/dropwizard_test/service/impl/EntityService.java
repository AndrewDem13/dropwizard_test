package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.CrudService;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

public class EntityService implements CrudService<Entity> {

    private Provider<CrudDao<Entity>> daoProvider;

    @Inject
    public EntityService(Provider<CrudDao<Entity>> daoProvider) {
        this.daoProvider = daoProvider;
    }

    @Override
    public void create(Entity entity) {
        if (daoProvider.get().get(entity.getId()) == null) {
            daoProvider.get().create(entity);
        } else {
            daoProvider.get().update(entity);
        }
    }

    @Override
    public Entity get(int id) {
        return daoProvider.get().get(id);
    }

    @Override
    public List<Entity> getAll() {
        return daoProvider.get().getAll();
    }

    @Override
    public Entity update(Entity entity) {
        if (daoProvider.get().get(entity.getId()) != null) {
            return daoProvider.get().update(entity);
        } else {
            daoProvider.get().create(entity);
            return entity;
        }
    }

    @Override
    public boolean delete(int id) {
        return daoProvider.get().delete(id);
    }
}
