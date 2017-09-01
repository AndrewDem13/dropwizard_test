package com.softserveinc.dropwizard_test.db.myBatis.mappers;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;

import java.util.List;

public interface EntityMapper extends CrudDao<Entity> {


    void create(Entity entity);


    Entity get(int id);


    List<Entity> getAll();


    void updateWithNoReturn(Entity entity);


    boolean delete(int id);
}
