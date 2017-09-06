package com.softserveinc.dropwizard_test.messaging.impl;

import com.rabbitmq.client.*;
import com.softserveinc.dropwizard_test.messaging.util.Constants;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.io.IOException;

public class RabbitConsumer {

    private int allUpdatesCount, allCreatesCount, currentUpdatesCount, currentCreatesCount;

    @Inject
    public RabbitConsumer(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body);
                if (message.contains("Created")) {
                    allCreatesCount++;
                    currentCreatesCount++;
                } else if (message.contains("Updated")) {
                    allUpdatesCount++;
                    currentUpdatesCount++;
                }
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(Constants.QUEUE_DEFAULT_NAME, true, consumer);
    }

    public int getAllUpdatesCount() {
        return allUpdatesCount;
    }

    public int getAllCreatesCount() {
        return allCreatesCount;
    }

    public int getCurrentUpdatesCount() {
        return currentUpdatesCount;
    }

    public void setCurrentUpdatesCount(int currentUpdatesCount) {
        this.currentUpdatesCount = currentUpdatesCount;
    }

    public int getCurrentCreatesCount() {
        return currentCreatesCount;
    }

    public void setCurrentCreatesCount(int currentCreatesCount) {
        this.currentCreatesCount = currentCreatesCount;
    }
}
