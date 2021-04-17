package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/04/2021
 * @ Version 1.0
 */

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
public class DeptProvider_10011 {
    public static void main(String[] args) {
        SpringApplication.run(DeptProvider_10011.class,args);
    }
}

