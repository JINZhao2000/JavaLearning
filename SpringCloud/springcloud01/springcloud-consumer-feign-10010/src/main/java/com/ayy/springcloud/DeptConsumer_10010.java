package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/04/2021
 * @ Version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.ayy.springcloud"})
public class DeptConsumer_10010 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_10010.class, args);
    }
}
