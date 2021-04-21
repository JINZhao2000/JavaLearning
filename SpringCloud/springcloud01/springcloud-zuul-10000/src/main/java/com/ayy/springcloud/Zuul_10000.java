package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/04/2021
 * @ Version 1.0
 */
@SpringBootApplication
@EnableZuulProxy
public class Zuul_10000 {
    public static void main(String[] args) {
        SpringApplication.run(Zuul_10000.class, args);
    }
}
