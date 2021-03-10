package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@Controller
public class IndexController {
    @RequestMapping("/hello")
    public String index(Model model){
        model.addAttribute("msg","<h1>Hello Thymeleaf</h1>");
        model.addAttribute("users", Arrays.asList("user1","user2"));
        return "hello";
    }
}
