package com.ayy.myrule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/04/2021
 * @ Version 1.0
 */
@Configuration
public class MyRule {
    @Bean("iRule")
    public IRule myRule(){
        return new MyRandomRule();
    }
}
