package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/03/2021
 * @ Version 1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String uname, String pwd) {
        session.setAttribute("loginfo", uname);
        return "main";
    }

    @RequestMapping("/tologin")
    public String tologin(HttpSession session, String uname, String pwd) {
        return "login";
    }
}
