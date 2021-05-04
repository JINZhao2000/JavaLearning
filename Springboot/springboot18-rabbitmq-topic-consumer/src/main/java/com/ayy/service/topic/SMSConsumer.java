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
                        value = RabbitMQConstant.SMS_QUEUE,
                        durable = "true",
                        autoDelete = "false"),
                exchange = @Exchange(
                        value = RabbitMQConstant.EXCHANGE_NAME,
                        type = ExchangeTypes.TOPIC),
                key = RabbitMQConstant.SMS_ROUTING_KEY))
public class SMSConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("SMS Topic "+message);
    }
}
