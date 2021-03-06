package com.ayy.controller;

import com.ayy.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/03/2021
 * @ Version 1.0
 */
@Controller
@RequestMapping("/user1")
public class UserController {
    @RequestMapping("/test1")
    public String test1(@RequestParam("uname") String name, Model model){
        System.out.println(name);
        model.addAttribute("msg",name);
        return "hello";
    }

    @RequestMapping("/test2")
    public String test2(User user){
        System.out.println(user);
        return "hello";
    }

}
