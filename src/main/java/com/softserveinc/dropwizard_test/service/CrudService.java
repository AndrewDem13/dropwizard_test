package com.softserveinc.dropwizard_test.service;

import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public interface CrudService<E> {
    void create(E entity);
    E get(String message);
    List<E> getAll();
    E update(String message, Entity entity);
    boolean delete(String message);
}
