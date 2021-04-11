package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class DeptProvider_10004 {
    public static void main(String[] args) {
        SpringApplication.run(DeptProvider_10004.class,args);
    }
}
