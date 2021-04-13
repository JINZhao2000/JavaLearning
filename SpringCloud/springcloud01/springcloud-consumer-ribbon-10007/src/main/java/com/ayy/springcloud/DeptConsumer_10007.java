package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class DeptConsumer_10007 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_10007.class, args);
    }
}
