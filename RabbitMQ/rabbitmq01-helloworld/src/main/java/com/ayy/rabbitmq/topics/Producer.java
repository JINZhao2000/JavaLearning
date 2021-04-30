package com.ayy.rabbitmq.topics;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 30/04/2021
 * @ Version 1.0
 */

public class Producer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();

            String exchangeName = "topic exchange";
            String type = "topic";
            String routingKey1 = "com.#";
            String routingKey2 = "*.ayy.*";
            String routingKey3 = "#.rabbitmq";
            String routingKey4 = "#.demo.#";

            String message = "hello topic";

            channel.exchangeDeclare(exchangeName, type);

            channel.queueDeclare("queue1",false,false,true,null);
            channel.queueDeclare("queue2",false,false,true,null);
            channel.queueDeclare("queue3",false,false,true,null);
            channel.queueDeclare("queue4",false,false,true,null);

            channel.queueBind("queue1", exchangeName, routingKey1);
            channel.queueBind("queue2", exchangeName, routingKey2);
            channel.queueBind("queue3", exchangeName, routingKey3);
            channel.queueBind("queue4", exchangeName, routingKey4);

//            channel.basicPublish(exchangeName, "com.ayy.rabbitmq", null, message.getBytes());
            channel.basicPublish(exchangeName, "com.ayy.rabbitmq.demo", null, message.getBytes());
            System.out.println("Success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
