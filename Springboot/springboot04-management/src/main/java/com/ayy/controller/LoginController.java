package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/03/2021
 * @ Version 1.0
 */
@Controller
public class LoginController {
    @RequestMapping("/user/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session){
        if("123456".equals(password)){
            session.setAttribute("loginUser",email);
            return "redirect:/main.html";
        }
        model.addAttribute("msg","Invalid password");
        return "index";
    }
}
