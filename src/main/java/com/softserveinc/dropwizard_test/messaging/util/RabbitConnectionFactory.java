package com.softserveinc.dropwizard_test.messaging.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.glassfish.hk2.api.Factory;

import java.io.IOException;

public class RabbitConnectionFactory implements Factory<Connection> {

    private ConnectionFactory connectionFactory;

    public RabbitConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Connection provide() {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void dispose(Connection instance) {
        try {
            instance.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
