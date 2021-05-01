package com.ayy.rabbitmq.work.roundrobin;

import com.ayy.rabbitmq.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Consumer2 {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtils.getConnection();
            channel = connection.createChannel();

            channel.queueDeclare("queue1", false, false, true, null);
            
            channel.basicQos(1);;
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    try {
                        System.out.println("Consumer2 - Message : "+new String(message.getBody(), StandardCharsets.UTF_8));
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    
                }
            });
            System.in.read();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            RabbitMQUtils.close(channel, connection);
        }
    }
}
