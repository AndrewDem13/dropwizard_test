package com.softserveinc.dropwizard_test.messaging.impl;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.softserveinc.dropwizard_test.messaging.AppPublisher;
import com.softserveinc.dropwizard_test.messaging.util.Constants;

import javax.inject.Inject;
import java.io.IOException;

public class RabbitPublisher implements AppPublisher {

    private Connection connection;
    private Channel channel;
    private Meter messagePublishes;

    @Inject
    public RabbitPublisher(Connection connection, MetricRegistry customMetricRegistry) throws IOException {
        this.connection = connection;
        channel = connection.createChannel();
        channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
        messagePublishes = customMetricRegistry.meter("sendMessage method calls");
    }

    @Override
    public void sendMessage(String message) {
        try {
            channel.basicPublish("", Constants.QUEUE_DEFAULT_NAME, null, message.getBytes());
            messagePublishes.mark();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentChannelNumber() {
        return channel.getChannelNumber();
    }

    public void changeChannelNumber(int channelNumber) throws Exception {
        channel.close();
        channel = connection.createChannel(channelNumber);
        channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
    }
}
