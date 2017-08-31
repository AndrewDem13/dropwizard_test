package com.softserveinc.dropwizard_test.db.myBatis;

import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.inject.Singleton;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

@Singleton
public class CustomSqlSessionFactory implements Factory<SqlSession> {

    private final SqlSessionFactory sqlSessionFactory;

    public CustomSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSession provide() {
        return sqlSessionFactory.openSession();
    }

    @Override
    public void dispose(SqlSession instance) {
        instance.close();
    }
}
