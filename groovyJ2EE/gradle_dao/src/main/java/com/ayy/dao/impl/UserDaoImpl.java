package com.ayy.dao.impl;

import com.ayy.dao.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	@Override
	public String save () {
		return "success to save";
	}
}
