package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 24/04/2021
 * @ Version 1.0
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication_10013 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication_10013.class, args);
    }
}
