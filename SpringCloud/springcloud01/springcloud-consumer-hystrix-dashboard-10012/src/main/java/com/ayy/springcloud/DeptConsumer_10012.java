package com.ayy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/04/2021
 * @ Version 1.0
 */
@SpringBootApplication
@EnableHystrixDashboard
public class DeptConsumer_10012 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_10012.class, args);
    }
}
