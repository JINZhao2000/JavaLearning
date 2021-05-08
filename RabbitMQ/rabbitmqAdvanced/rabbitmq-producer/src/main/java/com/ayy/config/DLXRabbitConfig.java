package com.ayy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/05/2021
 * @ Version 1.0
 */
@Configuration
public class DLXRabbitConfig {
    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(RabbitMQConstant.DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue(RabbitMQConstant.DEAD_LETTER_QUEUE, true, false, false);
    }

    @Bean
    public Binding deadLetterBinding(@Autowired Queue deadLetterQueue, @Autowired DirectExchange deadLetterExchange){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(RabbitMQConstant.DEAD_LETTER_ROUTING_KEY);
    }
}
