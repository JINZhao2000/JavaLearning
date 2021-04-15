package com.ayy.springcloud.config;

import com.ayy.myrule.MyRandomRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    /* iRule
     *     AvailabilityFilteringRule
     * RoundRobinRule 轮询
     * RandomRule 随机
     * WeightedResponseTimeRule
     * RetryRule
     */
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean("iRule")
    public IRule myRule(){
        return new MyRandomRule();
    }
}
