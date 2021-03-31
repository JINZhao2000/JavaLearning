package com.ayy.service;

import com.ayy.bean.User;
import com.ayy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 31/03/2021
 * @ Version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
