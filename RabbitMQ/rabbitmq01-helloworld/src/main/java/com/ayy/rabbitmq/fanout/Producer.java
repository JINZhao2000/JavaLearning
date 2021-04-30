package com.ayy.rabbitmq.fanout;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/04/2021
 * @ Version 1.0
 */

public class Producer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();

            String message = "hello fanout";
            String exchange = "fanout exchange";
            String routingKey = "";


            channel.exchangeDeclare(exchange, "fanout");

            channel.queueDeclare("queue1", false, false, true, null);
            channel.queueDeclare("queue2",false,false,true,null);
            channel.queueDeclare("queue3",false,false,true,null);


            channel.queueBind("queue1", exchange, routingKey);
            channel.queueBind("queue2", exchange, routingKey);
            channel.queueBind("queue3", exchange, routingKey);

            channel.basicPublish(exchange, routingKey, null, message.getBytes());
            System.out.println("Success");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
