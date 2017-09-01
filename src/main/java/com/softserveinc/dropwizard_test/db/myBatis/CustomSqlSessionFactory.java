package com.softserveinc.dropwizard_test.db.myBatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.glassfish.hk2.api.Factory;

public class CustomSqlSessionFactory implements Factory<SqlSession> {

    private final SqlSessionFactory sqlSessionFactory;

    public CustomSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSession provide() {
        return sqlSessionFactory.openSession(true);
    }

    @Override
    public void dispose(SqlSession instance) {
        instance.close();
    }
}
