package com.ayy.service.topic;

import com.ayy.config.RabbitMQConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/05/2021
 * @ Version 1.0
 */
@Service
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = RabbitMQConstant.EMAIL_QUEUE,
                        durable = "true",
                        autoDelete = "false"),
                exchange = @Exchange(
                        value = RabbitMQConstant.EXCHANGE_NAME,
                        type = ExchangeTypes.TOPIC),
                        key = RabbitMQConstant.EMAIL_ROUTING_KEY))
public class EmailConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("Email Topic "+message);
    }
}
