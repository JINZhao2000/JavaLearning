package com.ayy.springaop.semi_automatic;

public class UserServiceImpl implements UserService {
	@Override
	public void addUser () {
		System.out.println("Spring Proxy addUser");
	}

	@Override
	public void updateUser () {
		System.out.println("Spring Proxy updateUser");
	}

	@Override
	public void deleteUser () {
		System.out.println("Spring Proxy deleteUser");
	}
}
