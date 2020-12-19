package com.ayy.s01helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ ClassName HelloController
 * @ Description Web
 * @ Author Zhao JIN
 * @ Date 07/11/2020 21:48
 * @ Version 1.0
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
