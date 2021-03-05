package com.ayy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/03/2021
 * @ Version 1.0
 */
@Controller
public class RestfulController {
    @RequestMapping(value = "/add/{a}/{b}")
    public String rest1(@PathVariable int a,@PathVariable int b, Model model){
        model.addAttribute("msg","Result: "+(a+b));
        return "hello";
    }
}
