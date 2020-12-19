package com.ayy.service.impl;

import com.ayy.dao.UserDao;
import com.ayy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public String save () {
		return userDao.save();
	}
}
