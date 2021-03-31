package com.ayy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/03/2021
 * @ Version 1.0
 */
@Controller
public class MyController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","Hello, Shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/login")
    public String login(String uname, String pwd, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(uname, pwd);
        try  {
            subject.login(usernamePasswordToken);
            model.addAttribute("msg","Welcome "+uname);
            return "/index";
        } catch (UnknownAccountException e1){
            model.addAttribute("msg", "Unknown Account");
            return "login";
        } catch (IncorrectCredentialsException e2){
            model.addAttribute("msg", "Invalid password");
            return "login";
        }
    }

    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "Unauthorized";
    }
}
