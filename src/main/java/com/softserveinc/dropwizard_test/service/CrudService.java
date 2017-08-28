package com.softserveinc.dropwizard_test.service;

import java.util.List;

public interface CrudService<E> {
    void create(E entity);
    E get(int id);
    List<E> getAll();
    E update(E entity);
    boolean delete(int id);
}
