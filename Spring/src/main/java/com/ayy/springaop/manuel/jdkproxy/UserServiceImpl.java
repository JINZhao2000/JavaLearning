package com.ayy.springaop.manuel.jdkproxy;

public class UserServiceImpl implements UserService{
	@Override
	public void addUser () {
		System.out.println("JDK Proxy addUser");
	}

	@Override
	public void updateUser () {
		System.out.println("JDK Proxy updateUser");
	}

	@Override
	public void deleteUser () {
		System.out.println("JDK Proxy deleteUser");
	}
}
