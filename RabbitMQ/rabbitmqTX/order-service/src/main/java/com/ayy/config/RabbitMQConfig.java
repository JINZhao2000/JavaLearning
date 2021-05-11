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
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("order_direct_exchange");
    }

    @Bean
    public Queue queue(){
        return new Queue("order.queue",true);
    }

    @Bean
    public Binding binding(@Autowired DirectExchange directExchange, @Autowired Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("order");
    }
}
