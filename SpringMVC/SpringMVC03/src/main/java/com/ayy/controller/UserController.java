package com.ayy.controller;

import com.alibaba.fastjson.JSON;
import com.ayy.bean.User;
import com.ayy.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/03/2021
 * @ Version 1.0
 */
@RestController
public class UserController {
//    @RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")
    @RequestMapping("/json1")
    public String json1() throws JsonProcessingException {
        User user = new User("user1", 18, "n");
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        return userJson;
    }

    @RequestMapping("/json2")
    public String json2() throws JsonProcessingException {
        User user1 = new User("user1", 18, "n");
        User user2 = new User("user2", 18, "n");
        User user3 = new User("user3", 18, "n");
        User user4 = new User("user4", 18, "n");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        return new ObjectMapper().writeValueAsString(users);
    }

    @RequestMapping("/json3")
    public String json3() throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ObjectMapper().writeValueAsString(sdf.format(date));
    }

    @RequestMapping("/json4")
    public String json4() throws JsonProcessingException {
        return JsonUtil.getJson(new Date());
    }

    @RequestMapping("/json5")
    public String json5() throws JsonProcessingException {
        User user1 = new User("user1", 18, "n");
        User user2 = new User("user2", 18, "n");
        User user3 = new User("user3", 18, "n");
        User user4 = new User("user4", 18, "n");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return JSON.toJSONString(users);
    }
}
