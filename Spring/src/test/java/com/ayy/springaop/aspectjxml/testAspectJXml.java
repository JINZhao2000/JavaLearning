package com.ayy.springaop.aspectjxml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testAspectJXml {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("AOPContext3.xml");
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
