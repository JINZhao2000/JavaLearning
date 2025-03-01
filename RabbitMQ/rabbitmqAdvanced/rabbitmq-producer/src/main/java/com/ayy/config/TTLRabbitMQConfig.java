package com.ayy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/05/2021
 * @ Version 1.0
 */
@Configuration
public class TTLRabbitMQConfig {
    @Bean
    public DirectExchange ttlDirectExchange(){
        return new DirectExchange(RabbitMQConstant.TTL_EXCHANGE, true, false);
    }

    @Bean
    public Queue ttlQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange",RabbitMQConstant.DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key",RabbitMQConstant.DEAD_LETTER_ROUTING_KEY);
        return new Queue(RabbitMQConstant.TTL_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding ttlBinding(@Autowired DirectExchange ttlDirectExchange, @Autowired Queue ttlQueue){
        return BindingBuilder.bind(ttlQueue).to(ttlDirectExchange).with(RabbitMQConstant.TTL_ROUTING_KEY);
    }

    @Bean
    public Queue ttlMessageQueue(){
        return new Queue(RabbitMQConstant.TTL_MESSAGE_QUEUE, true, false, false, null);
    }

    @Bean
    public Binding ttlMessageBinding(@Autowired DirectExchange ttlDirectExchange, @Autowired Queue ttlMessageQueue){
        return BindingBuilder.bind(ttlMessageQueue).to(ttlDirectExchange).with(RabbitMQConstant.TTL_MESSAGE_ROUTING_KEY);
    }
}
