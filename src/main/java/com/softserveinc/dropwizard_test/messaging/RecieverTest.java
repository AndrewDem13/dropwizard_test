package com.softserveinc.dropwizard_test.messaging;

import com.rabbitmq.client.*;
import com.softserveinc.dropwizard_test.messaging.util.Constants;

import java.io.IOException;

public class RecieverTest {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constants.QUEUE_DEFAULT_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(Constants.QUEUE_DEFAULT_NAME, true, consumer);
    }

}
