package com.ayy.rabbitmq.direct;

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

            String message = "hello direct";
            String exchangeName = "direct exchange";
            String type = "direct";

            String routingKey1 = "email";
            String routingKey2 = "sms";
            String routingKey3 = "email";

            channel.exchangeDeclare(exchangeName, type);

            channel.queueDeclare("queue1", false, false, true, null);
            channel.queueDeclare("queue2", false, false, true, null);
            channel.queueDeclare("queue3", false, false, true, null);

            channel.queueBind("queue1",exchangeName,routingKey1);
            channel.queueBind("queue2",exchangeName,routingKey2);
            channel.queueBind("queue3",exchangeName,routingKey3);

            channel.basicPublish(exchangeName, routingKey1, null, message.getBytes());
            System.out.println("Success");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
