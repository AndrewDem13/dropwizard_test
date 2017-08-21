package com.softserveinc.dropwizard_test.db.impl;

import com.softserveinc.dropwizard_test.db.BasicDao;
import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public class EntityDao implements BasicDao<Entity> {
    @Override
    public Entity createEntity(Entity entity) {
        return null;
    }

    @Override
    public Entity getEntity(Long id) {
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public Entity updateEntity(Entity entity) {
        return null;
    }

    @Override
    public boolean deleteEntity(Long id) {
        return false;
    }
}
