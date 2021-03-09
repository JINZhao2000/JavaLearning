package com.ayy.controller;

import com.alibaba.fastjson.JSON;
import com.ayy.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/03/2021
 * @ Version 1.0
 */
@RestController
public class AjaxController {
    @RequestMapping("/t1")
    public String test(){
        return "hello";
    }

    @RequestMapping("/test2")
    public void test2(String name, HttpServletResponse resp) throws IOException {
        if(name.equals("abc")){
            resp.getWriter().print(true);
        }else{
            resp.getWriter().print(false);
        }
    }

    @RequestMapping("/test3")
    public String test3(){
        List<User> users = new ArrayList<>();
        users.add(new User("user1",18,"M"));
        users.add(new User("user2",19,"M"));
        users.add(new User("user3",20,"F"));
        users.add(new User("user4",21,"F"));
        return JSON.toJSONString(users);
    }

    @RequestMapping("/validate")
    public String validate(String name, String pwd){
        String msg = "";
        if(null!=name){
            if("admin".equals(name)){
                msg = "ok";
            }else {
                msg = "nok";
            }
        }
        if(null!=pwd){
            if("123456".equals(pwd)){
                msg = "ok";
            }else {
                msg = "nok";
            }
        }
        return msg;
    }
}
