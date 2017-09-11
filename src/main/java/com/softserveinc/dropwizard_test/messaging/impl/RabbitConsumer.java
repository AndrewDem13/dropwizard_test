package com.softserveinc.dropwizard_test.messaging.impl;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.rabbitmq.client.*;
import com.softserveinc.dropwizard_test.messaging.util.Constants;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import java.io.IOException;

public class RabbitConsumer implements Feature {

    @Inject
    private MetricRegistry customMetricRegistry;

    @Inject
    private Connection connection;

    @Override
    public boolean configure(FeatureContext context) {
        try {
            Counter totalCreatesCounter = customMetricRegistry.counter("Total Creates Counter");
            Counter totalUpdatesCount =  customMetricRegistry.counter("Total Updates Count");
            customMetricRegistry.gauge("Updates To Creates Ratio", () -> new RatioGauge() {
                @Override
                protected Ratio getRatio() {
                    return Ratio.of(totalUpdatesCount.getCount(), totalCreatesCounter.getCount());
                }
            });

            Channel channel = connection.createChannel();
            channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    if (message.contains("CREATED")) {
                        totalCreatesCounter.inc();
                    } else if (message.contains("UPDATED")) {
                        totalUpdatesCount.inc();
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
