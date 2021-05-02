package com.ayy.service.fanout;

import com.ayy.config.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/05/2021
 * @ Version 1.0
 */
@Service
@RabbitListener(queues = {RabbitMQConstant.EMAIL_QUEUE})
public class EmailConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("Email Fanout "+message);
    }
}
