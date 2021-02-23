package com.ayy.service.impl;

import com.ayy.bean.User;
import com.ayy.dao.UserDao;
import com.ayy.service.UserService;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
public class UserServiceImpl implements UserService {
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void register(User user) {
        dao.save(user);
    }
}