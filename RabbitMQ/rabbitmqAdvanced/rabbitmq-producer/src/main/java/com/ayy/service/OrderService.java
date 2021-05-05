package com.ayy.service;

import com.ayy.config.RabbitMQConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/05/2021
 * @ Version 1.0
 */
@Service
public class OrderService {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void makeOrderTTL(String uid, String productId, int num){
        String orderId = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend(RabbitMQConstant.TTL_EXCHANGE, RabbitMQConstant.TTL_ROUTING_KEY, orderId);
    }
}
