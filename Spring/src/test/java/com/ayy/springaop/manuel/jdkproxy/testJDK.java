package com.ayy.springaop.manuel.jdkproxy;

import org.junit.Test;

public class testJDK {
	@Test
	public void demo01(){
		UserService userService = MyBeanFactory.createService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
