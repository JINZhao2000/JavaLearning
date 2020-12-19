package com.ayy.springaop.semi_automatic;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testSpringSemiAuto {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("AOPContext1.xml");

		UserService userService = (UserService) applicationContext.getBean("proxyService");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
