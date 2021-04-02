package com.ayy.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/04/2021
 * @ Version 1.0
 */
@Controller
public class HelloController {
    @RequestMapping({"/hello","/"})
    @RequestBody
    public String hello(){
        return "Hello Swagger";
    }

}
