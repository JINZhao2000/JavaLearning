package com.ayy.rabbitmq.work.fair;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 01/05/2021
 * @ Version 1.0
 */

public class Producer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();

            channel.queueDeclare("queue1", false, false, true, null);

            for (int i = 0; i < 20; i++) {
                String msg = "Message Num "+i;
                channel.basicPublish("","queue1",null, msg.getBytes());
            }
            System.out.println("Success");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
