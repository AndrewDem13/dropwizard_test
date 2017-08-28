package com.softserveinc.dropwizard_test.db;

import java.util.List;

public interface CrudDao<E> {
    void create(E entity);
    E get(int id);
    List<E> getAll();
    E update(E entity);
    boolean delete(int id);
}
