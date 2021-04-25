package com.ayy.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/04/2021
 * @ Version 1.0
 */
@RestController
public class ConfigController {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServer;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/config")
    public String getConfig(){
        return "appName: "+applicationName+"\neureka: "+eurekaServer+"\nport: "+port;
    }
}
