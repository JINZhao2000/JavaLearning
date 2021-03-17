package com.ayy.controller;

import com.ayy.bean.User;
import com.ayy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/03/2021
 * @ Version 1.0
 */
@RestController
public class UserController {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/queryUserList")
    public String queryUserList(){
        List<User> users = userMapper.queryUserList();
        return users.toString();
    }

    @RequestMapping("/addUser")
    public String addUser(){
        User user = new User();
        user.setUname("USER333");
        user.setPwd("345678");
        userMapper.addUser(user);
        return "ok";
    }

    @RequestMapping("/updateUser")
    public String updateUser(){
        User user = new User(1,"USER3111","654321");
        userMapper.updateUser(user);
        return "ok";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(){
        userMapper.deleteUser(7);
        return "ok";
    }
}
