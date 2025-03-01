package com.ayy.rabbitmq.simple;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/04/2021
 * @ Version 1.0
 */

public class Consumer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();
            String queueName = "Queue1";

            channel.basicConsume(queueName, true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println("Message is :" + new String(message.getBody(), StandardCharsets.UTF_8));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    System.out.println("Message failed");
                }
            });
            System.out.println("Begin");
            System.in.read(); // block
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
