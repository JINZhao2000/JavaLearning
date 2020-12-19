package com.ayy.springaop.manuel.cglib;

import org.junit.Test;

public class testCGLIB {
	@Test
	public void demo01(){
		UserServiceImpl userService = MyBeanFactory.createService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
