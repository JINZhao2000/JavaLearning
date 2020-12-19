package com.ayy.springioc.impl;

import com.ayy.springioc.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser () {
        System.out.println("addUser");
    }
}
