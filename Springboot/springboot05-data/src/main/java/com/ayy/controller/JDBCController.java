package com.ayy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/03/2021
 * @ Version 1.0
 */
@RestController
public class JDBCController {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        String sql = "select * from mb_user";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/add")
    public String addUser(){
        String sql = "insert into mb_user(uname, pwd) values('USER234','123456')";
        jdbcTemplate.update(sql);
        return "add - ok";
    }

    @SuppressWarnings("all")
    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id){
        String sql = "update mb_user set uname = 'USER7' where uid = ?";
        jdbcTemplate.update(sql,new Object[]{id});
        return "update - ok";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        String sql = "delete from mb_user where uid = ?";
        jdbcTemplate.update(sql,id);
        return "delete - ok";
    }
}
