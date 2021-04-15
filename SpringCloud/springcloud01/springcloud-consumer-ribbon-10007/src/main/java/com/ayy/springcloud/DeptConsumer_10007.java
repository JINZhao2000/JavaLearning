package com.ayy.springcloud;

import com.ayy.myrule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
// @RibbonClient after @SpringBootApplication
@RibbonClient(name = "SPRINGCLOUD-PROVIDER-DEPT",configuration = MyRule.class)
public class DeptConsumer_10007 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_10007.class, args);
    }
}


