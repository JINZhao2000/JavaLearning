package com.ayy.rabbitmq.fanout;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/04/2021
 * @ Version 1.0
 */

public class Consumer implements Runnable {
    private final String queueName;

    public static void main(String[] args) {
        new Thread(new Consumer("queue1")).start();
        new Thread(new Consumer("queue2")).start();
        new Thread(new Consumer("queue3")).start();
    }

    public Consumer(String queueName){
        this.queueName = queueName;
    }

    @Override
    public void run() {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();

            channel.basicConsume(queueName, true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println(queueName+" Message : "+new String(message.getBody(), StandardCharsets.UTF_8));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    System.out.println(queueName+" Message failed");
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
