package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/03/2021
 * @ Version 1.0
 */

@Controller
public class RootController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "/views/login";
    }

    @RequestMapping("/level/{id}/{page}")
    public String level(@PathVariable("id") int id, @PathVariable("page") int page) {
        return "/views/level" + id + "/" + page;
    }
}