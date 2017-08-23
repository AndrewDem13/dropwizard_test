package com.softserveinc.dropwizard_test.service;

import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public interface BasicService<E> {
    void createEntity(E entity);
    E getEntity(String message);
    List<E> getAll();
    E updateEntity(String message, Entity entity);
    boolean deleteEntity(String message);
}