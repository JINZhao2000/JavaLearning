package Cours1.Factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testFactory {
	@Test
	public void demo1(){
		MyBeanFactory myBeanFactory = new MyBeanFactory();
		UserService3 userService = myBeanFactory.createService();
		userService.addUser();
	}

	@Test
	public void demo2(){
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/Java/IDEA_Projet/ProjetSpring/src/Cours1/Factory/applicationContext.xml");
		UserService3 userService = applicationContext.getBean("userService", UserService3.class);
		userService.addUser();
	}
}
