package com.softserveinc.dropwizard_test.db;

import java.util.List;

public interface CrudDao<E> {
    void create(E entity);
    E get(String message);
    List<E> getAll();
    E update(String message, E entity);
    boolean delete(String message);
}
