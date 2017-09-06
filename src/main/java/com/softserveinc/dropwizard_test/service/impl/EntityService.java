package com.softserveinc.dropwizard_test.service.impl;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.messaging.AppPublisher;
import com.softserveinc.dropwizard_test.service.CrudService;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

public class EntityService implements CrudService<Entity> {

    private AppPublisher publisher;
    private Provider<CrudDao<Entity>> daoProvider;

    @Inject
    public EntityService(AppPublisher publisher, Provider<CrudDao<Entity>> daoProvider) {
        this.publisher = publisher;
        this.daoProvider = daoProvider;
    }

    @Override
    public void create(Entity entity) {
        if (daoProvider.get().get(entity.getId()) == null) {
            daoProvider.get().create(entity);
            sendCreatedMessage(entity);
        } else {
            daoProvider.get().update(entity);
            sendUpdatedMessage(entity);
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
            Entity updatedEntity = daoProvider.get().update(entity);
            sendUpdatedMessage(updatedEntity);
            return updatedEntity;
        } else {
            daoProvider.get().create(entity);
            sendCreatedMessage(entity);
            return entity;
        }
    }

    @Override
    public boolean delete(int id) {
        return daoProvider.get().delete(id);
    }

    private void sendCreatedMessage(Entity entity) {
        String message = String.format("CREATED: Entity with ID %d and message: \"%s\". Saved through %s",
                entity.getId(), entity.getMessage(), daoProvider.get().getClass().getSimpleName());
        publisher.sendMessage(message);

    }

    private void sendUpdatedMessage(Entity entity) {
        String message = String.format("Updated: Entity with ID %d and message: \"%s\". Updated through %s",
                entity.getId(), entity.getMessage(), daoProvider.get().getClass().getSimpleName());
        publisher.sendMessage(message);
    }
}
