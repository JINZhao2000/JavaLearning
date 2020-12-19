package com.ayy.springaop.aspectjxml;

public class UserServiceImpl implements UserService {
	@Override
	public void addUser () {
		System.out.println("AspectJ XML addUser");
	}

	@Override
	public String updateUser () {
		System.out.println("AspectJ XML updateUser");
		return "testReturning";
	}

	@Override
	public void deleteUser () {
		System.out.println("AspectJ XML deleteUser");
	}
}
