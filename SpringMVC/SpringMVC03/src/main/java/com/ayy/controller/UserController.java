package com.ayy.controller;

import com.ayy.bean.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/03/2021
 * @ Version 1.0
 */
@Controller
public class UserController {
//    @RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")
    @RequestMapping("/json1")
    @ResponseBody
    public String json1() throws JsonProcessingException {
        User user = new User("user1", 18, "n");
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        return userJson;
    }
}
