package com.ayy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitMQConstant.EXCHANGE_NAME, false, true);
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
    public Binding smsBinding(@Autowired Queue smsQueue, @Autowired FanoutExchange fanoutExchange){
        return BindingBuilder.bind(smsQueue).to(fanoutExchange);
    }

    @Bean
    public Binding emailBinding(@Autowired Queue emailQueue, @Autowired FanoutExchange fanoutExchange){
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }
}
