package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/03/2021
 * @ Version 1.0
 */
@Controller
public class ServletTestController {
    @RequestMapping("/stc/st")
    public String sessionTest(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        System.out.println(session.getId());
        return "hello";
    }
}
