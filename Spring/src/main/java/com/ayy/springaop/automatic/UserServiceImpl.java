package com.ayy.springaop.automatic;

public class UserServiceImpl implements UserService {
	@Override
	public void addUser () {
		System.out.println("Spring AOP addUser");
	}

	@Override
	public void updateUser () {
		System.out.println("Spring AOP updateUser");
	}

	@Override
	public void deleteUser () {
		System.out.println("Spring AOP deleteUser");
	}
}
