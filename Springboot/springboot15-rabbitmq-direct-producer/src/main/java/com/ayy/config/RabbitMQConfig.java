package com.ayy.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/05/2021
 * @ Version 1.0
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitMQConstant.EXCHANGE_NAME, false, true);
    }

    @Bean
    public Queue smsQueue(){
        return new Queue(RabbitMQConstant.SMS_QUEUE, false);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(RabbitMQConstant.EMAIL_QUEUE, false);
    }

    @Bean
    public Binding smsBinding(@Autowired Queue smsQueue, @Autowired DirectExchange directExchange){
        return BindingBuilder.bind(smsQueue).to(directExchange).with(RabbitMQConstant.SMS_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding(@Autowired Queue emailQueue, @Autowired DirectExchange directExchange){
        return BindingBuilder.bind(emailQueue).to(directExchange).with(RabbitMQConstant.EMAIL_ROUTING_KEY);
    }
}
