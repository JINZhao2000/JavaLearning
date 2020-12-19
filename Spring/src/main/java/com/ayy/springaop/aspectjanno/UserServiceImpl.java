package com.ayy.springaop.aspectjanno;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Override
	public void addUser () {
		System.out.println("AspectJ Anno addUser");
	}

	@Override
	public String updateUser () {
		System.out.println("AspectJ Anno updateUser");
		return "testReturning";
	}

	@Override
	public void deleteUser () {
		System.out.println("AspectJ Anno deleteUser");
	}
}
