package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer_10003 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer_10003.class, args);
    }
}
