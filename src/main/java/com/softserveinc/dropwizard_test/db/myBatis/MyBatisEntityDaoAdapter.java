package com.softserveinc.dropwizard_test.db.myBatis;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.myBatis.mappers.EntityMapper;
import com.softserveinc.dropwizard_test.entity.Entity;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.List;

public class MyBatisEntityDaoAdapter implements CrudDao<Entity> {

    private SqlSession sqlSession;

    @Inject
    public MyBatisEntityDaoAdapter(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void create(Entity entity) {
        sqlSession.getMapper(EntityMapper.class).create(entity);
        sqlSession.commit();
    }

    @Override
    public Entity get(int id) {
        return sqlSession.getMapper(EntityMapper.class).get(id);
    }

    @Override
    public List<Entity> getAll() {
        return sqlSession.getMapper(EntityMapper.class).getAll();
    }

    @Override
    public Entity update(Entity entity) {
        sqlSession.getMapper(EntityMapper.class).update(entity);
        sqlSession.commit();
        return entity;
    }

    @Override
    public boolean delete(int id) {
        boolean result = sqlSession.getMapper(EntityMapper.class).delete(id);
        sqlSession.commit();
        return result;
    }
}
