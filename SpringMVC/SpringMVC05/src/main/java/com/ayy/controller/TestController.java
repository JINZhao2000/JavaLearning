package com.ayy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/03/2021
 * @ Version 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/test1")
    public String test1(){
        System.out.println("test");
        return "ok";
    }
}
