package com.softserveinc.dropwizard_test.db;

import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public abstract class EntityDao implements CrudDao<Entity> {
    @Override
    public abstract void createEntity(Entity entity);

    @Override
    public abstract Entity getEntity(String message);

    @Override
    public abstract List<Entity> getAll();

    @Override
    public abstract Entity updateEntity(String message, Entity entity);

    @Override
    public abstract boolean deleteEntity(String message);
}
