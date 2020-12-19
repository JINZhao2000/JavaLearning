package com.ayy.springaop.aspectjanno;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testAspectJAnno {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("AOPContext4.xml");
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
