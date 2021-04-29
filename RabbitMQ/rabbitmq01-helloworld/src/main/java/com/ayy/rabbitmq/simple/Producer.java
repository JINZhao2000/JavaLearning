package com.ayy.rabbitmq.simple;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/04/2021
 * @ Version 1.0
 */

public class Producer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
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
            /*
             * @params1 exchange
             * @params2 routing key
             * @params3 status
             * @params4 message
             */
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("Message success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
