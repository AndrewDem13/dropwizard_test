package com.softserveinc.dropwizard_test.db.util;

public interface DbSwitcherMBean {
    void setSqlDb(boolean b);
    boolean getSqlDb();
    void sayHello();
}
