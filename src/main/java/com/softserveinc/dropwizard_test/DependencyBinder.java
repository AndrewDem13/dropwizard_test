package com.softserveinc.dropwizard_test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.mongo.MongoEntityDao;
import com.softserveinc.dropwizard_test.db.mongo.MongoEntityDaoAdapter;
import com.softserveinc.dropwizard_test.db.myBatis.CustomSqlSessionFactory;
import com.softserveinc.dropwizard_test.db.myBatis.MyBatisEntityDaoAdapter;
import com.softserveinc.dropwizard_test.db.util.DaoFactory;
import com.softserveinc.dropwizard_test.db.util.DbSwitcher;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

public class DependencyBinder extends AbstractBinder {

    private final AppConfiguration configuration;

    public DependencyBinder(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        /*
        MongoDB configuration
         */
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase database = configuration.getMongoClient().getDatabase(configuration.mongodb).withCodecRegistry(pojoCodecRegistry);
        bind(database).to(MongoDatabase.class);

        /*
        MyBatis configuration
         */
        String myBatisConfigPath = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory = null;
        try {
            InputStream configStream = Resources.getResourceAsStream(myBatisConfigPath);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configStream);
        } catch (IOException e) {
            System.out.println("Error occurred while reading MyBatis config file: " + e.getMessage());
        }
        if (sqlSessionFactory != null) {
            bindFactory(new CustomSqlSessionFactory(sqlSessionFactory)).to(SqlSession.class);
        }

        /*
        DB Switcher configuration
        NoSQL (MongoDB) is default
         */
        DbSwitcher dbSwitcher = new DbSwitcher();
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            platformMBeanServer.registerMBean(dbSwitcher, new ObjectName("DbSwitcher", "name", "DbSwitcher"));
        } catch (Exception e) {
            System.out.println("Error occurred while registering DB MBean util: " + e.getMessage());
        }
        bind(dbSwitcher).to(DbSwitcher.class);
        bindFactory(DaoFactory.class).to(new TypeLiteral<CrudDao<Entity>>() {});

        bind(MongoEntityDao.class).to(MongoEntityDao.class).in(Singleton.class);
        bind(MongoEntityDaoAdapter.class).to(MongoEntityDaoAdapter.class).in(Singleton.class);
        bind(MyBatisEntityDaoAdapter.class).to(MyBatisEntityDaoAdapter.class).in(Singleton.class);
        bind(EntityService.class).to(EntityService.class).in(Singleton.class);
    }
}
