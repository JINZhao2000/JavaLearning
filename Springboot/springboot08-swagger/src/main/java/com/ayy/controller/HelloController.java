package com.ayy.controller;

import com.ayy.bean.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation("Method API")
    @RequestBody
    public String hello(){
        return "Hello Swagger";
    }

    @GetMapping("/user")
    @RequestBody
    public User user(@ApiParam("username") String uname){
        return new User();
    }

    @PostMapping("/user")
    @RequestBody
    public User userPost(@ApiParam("username") String uname){
        return new User();
    }
}
