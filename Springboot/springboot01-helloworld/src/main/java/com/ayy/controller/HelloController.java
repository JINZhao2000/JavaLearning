package com.ayy.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "Hello SpringBoot";
    }
}
