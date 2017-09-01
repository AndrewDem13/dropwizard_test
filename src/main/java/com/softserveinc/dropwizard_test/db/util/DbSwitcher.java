package com.softserveinc.dropwizard_test.db.util;

public class DbSwitcher implements DbSwitcherMBean {
    private boolean isSqlDb;

    @Override
    public void setSqlDb(boolean b) {
        isSqlDb = b;
    }

    @Override
    public boolean getSqlDb() {
        return isSqlDb;
    }

    @Override
    public void sayHello() {
        System.out.println("=========================  Hello! ======================");
    }
}
