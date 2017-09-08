package com.softserveinc.dropwizard_test.messaging.impl;

import com.rabbitmq.client.*;
import com.softserveinc.dropwizard_test.messaging.util.Constants;
import com.softserveinc.dropwizard_test.service.CreateUpdateCounter;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import java.io.IOException;

public class RabbitConsumer implements Feature {

    @Inject
    private CreateUpdateCounter counter;

    @Inject Connection connection;

    @Override
    public boolean configure(FeatureContext context) {
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    if (message.contains("CREATED")) {
                        counter.increaseCreatesCount();
                    } else if (message.contains("UPDATED")) {
                        counter.increaseUpdatesCount();
                    }
                    System.out.println(" [=== Rabbit Received ===] '" + message + "'");
                }
            };
            channel.basicConsume(Constants.QUEUE_DEFAULT_NAME, true, consumer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
