package com.softserveinc.dropwizard_test.db;

import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public interface BasicDao<E> {
    E createEntity(E entity);
    E getEntity(Long id);
    List<E> getAll();
    E updateEntity(Entity entity);
    boolean deleteEntity(Long id);
}
