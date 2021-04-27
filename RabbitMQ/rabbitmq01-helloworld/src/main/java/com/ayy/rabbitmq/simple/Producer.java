package com.ayy.rabbitmq.simple;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/04/2021
 * @ Version 1.0
 */

public class Producer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.load(RabbitMQUtils.getProperties());

        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            String queueName = "Queue1";
            /*
             * @params1 name of queue
             * @params2 durable
             * @params3 exclusion
             * @params4 auto delete
             * @params5 args
             */
            channel.queueDeclare(queueName,false,false,false,null);
            String message = "Hello World";
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("Message success");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
