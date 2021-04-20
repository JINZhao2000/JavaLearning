package com.ayy.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

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


    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServlet(){
        ServletRegistrationBean<HystrixMetricsStreamServlet> bean = new ServletRegistrationBean<>(new HystrixMetricsStreamServlet());
        bean.addUrlMappings("/actuator/hystrix.stream");
        return bean;
    }
}

