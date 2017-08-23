package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.BasicService;

import java.util.List;

public class IndexService implements BasicService<Entity> {
    @Override
    public void createEntity(Entity entity) {

    }

    @Override
    public Entity getEntity(String message) {
        return new Entity(message);
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public Entity updateEntity(String message, Entity entity) {
        return null;
    }

    @Override
    public boolean deleteEntity(String message) {
        return false;
    }
}
