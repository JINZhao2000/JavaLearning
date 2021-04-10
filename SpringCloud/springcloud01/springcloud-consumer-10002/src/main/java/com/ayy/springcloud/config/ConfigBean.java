package com.ayy.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
