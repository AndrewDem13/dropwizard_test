package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.BasicService;

import java.util.List;

public class EntityService implements BasicService<Entity> {
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
