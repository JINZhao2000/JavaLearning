package com.ayy.springaop.automatic;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testSpringAOP {
	@Test
	public void demo01(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("AOPContext2.xml");
		UserService userService = (UserService)applicationContext.getBean("userService");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
